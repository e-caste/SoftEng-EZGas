package it.polito.ezgas.service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

import static org.junit.Assert.*;

import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// IMPORTANT NOTE: these tests can't run if the website is up and running,
//                 since they use the same port to connect to the DB
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceimplTests {
    @Autowired
    UserRepository userRepository;

    static Connection db;
    static Statement st;
    static ResultSet backup;
    static String sqlSelectAllUsers = "SELECT * FROM USER";
    static String sqlDropUserTable = "DROP TABLE IF EXISTS USER";
    static String sqlCreateUserTable = "CREATE TABLE USER " +
                                       "(user_id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                                       "admin BOOLEAN, " +
                                       "email VARCHAR(255), " +
                                       "password VARCHAR(255), " +
                                       "reputation INTEGER, " +
                                       "user_name VARCHAR(255))";
    static List<String> sqlInsertUsers = Arrays.asList(
            "INSERT INTO USER VALUES (1, TRUE, 'admin@ezgas.com', 'admin', 5, 'admin')",
            "INSERT INTO USER VALUES (2, FALSE, 'asd@asd.asd', 'asd', 0, 'asd')"
    );

    // using UserService instead of UserServiceimpl since it's the interface
    @Autowired
    private UserService userService;

    Integer existingAdminUserId, existingUserId, nonExistingUserId;
    Boolean existingAdminUserAdmin, existingUserAdmin, nonExistingUserAdmin;
    private User existingAdminUser, existingUser, nonExistingUser;
    private UserDto existingAdminUserDto, existingUserDto, nonExistingUserDto;

    @PostConstruct
    @BeforeClass  // run only once
    public static void setUpDatabase() throws SQLException {
        db = DriverManager.getConnection("jdbc:h2:./data/test", "sa", "password");
        st = db.createStatement();
//        backup = st.executeQuery(sqlSelectAllUsers);
        st.executeUpdate(sqlDropUserTable);
        st.executeUpdate(sqlCreateUserTable);
        for (String sql : sqlInsertUsers) {
            st.executeUpdate(sql);
        }

        // uncomment following lines for debugging
//        ResultSet rs = st.executeQuery(sqlSelectAllUsers);
//        while (rs.next()) {
//            System.err.println("ID: " + rs.getInt("user_id") + " " +
//                                "ADMIN: " + rs.getBoolean("admin") + " " +
//                                "EMAIL: " + rs.getString("email") + " " +
//                                "PASSWORD: " + rs.getString("password") + " " +
//                                "REPUTATION: " + rs.getInt("reputation") + " " +
//                                "USERNAME: " + rs.getString("user_name")
//                    );
//        }
//
//        System.exit(33);
    }

    @Before  // run before each test
    public void setUp() {

        // admin user with existing id in the database
        existingAdminUser = new User("admin", "admin", "admin@ezgas.com", 5);
        existingAdminUserId = 1;
        existingAdminUserAdmin = true;
        existingAdminUser.setUserId(existingAdminUserId);
        existingAdminUser.setAdmin(existingAdminUserAdmin);
        existingAdminUserDto = UserConverter.convertEntityToDto(existingAdminUser);

        // user with existing id in the database
        existingUser = new User("asd", "asd", "asd@asd.asd", 0);
        existingUserId = 2;
        existingUserAdmin = false;
        existingUser.setUserId(existingUserId);
        existingUser.setAdmin(existingUserAdmin);
        existingUserDto = UserConverter.convertEntityToDto(existingUser);

        // user with non-existing id in the database
        nonExistingUser = new User("test", "test", "test@test.test", 0);
        nonExistingUserId = 3;
        nonExistingUserAdmin = false;
        nonExistingUser.setUserId(nonExistingUserId);
        nonExistingUser.setAdmin(nonExistingUserAdmin);
        nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);

//        userService.getAllUsers().forEach(System.out::println);
    }

    @AfterClass  // run only once
    public static void tearDown() throws SQLException {
        // manually reset DB to pre-test state
        // because can't use savepoint and rollback feature with H2 in server mode

        // comment lines below if using test db instead of memo
//        List<String> sqlInsertBackupUsers = new ArrayList<>();
//        st.executeUpdate(sqlDropUserTable);
//        while (backup.next()) {
//            sqlInsertBackupUsers.add("INSERT INTO USER VALUES " +
//                                     "(" + backup.getInt("user_id") +
//                                     ", " + backup.getBoolean("admin") +
//                                     ", '" + backup.getString("email") + "'" +
//                                     ", '" + backup.getString("password") +"'" +
//                                     ", " + backup.getInt("reputation") +
//                                     ", '" + backup.getString("user_name") + "'" +
//                                     ")");
//        }
//        for (String sql : sqlInsertBackupUsers) {
//            st.executeUpdate(sql);
//        }

        st.close();
        db.close();
    }

    @Test
    public void testGetUserById() throws InvalidUserException {
        assertTrue(existingAdminUserDto.equals(userService.getUserById(existingAdminUserId)));
        // assertThrows() - not available in this version of JUnit4
        try {
            userService.getUserById(nonExistingUserId);
            fail("Expected InvalidUserException for userId " + nonExistingUserId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testSaveUser() {

        // user does not exist in database
        // and is valid -> save new
        nonExistingUser.setAdmin(false);
        nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);
        assertTrue(nonExistingUserDto.equalsIgnoreUserId(userService.saveUser(nonExistingUserDto)));

        // user exists in database
        // user id exists and email is same -> update user fields in database
        existingUser.setPassword("newPassword");
        existingUserDto = UserConverter.convertEntityToDto(existingUser);
        assertTrue(existingUserDto.equals(userService.saveUser(existingUserDto)));
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        // get users from DB
        List<UserDto> usersDB = new ArrayList<>();
        ResultSet rs = st.executeQuery(sqlSelectAllUsers);
        while (rs.next()) {
            UserDto userDto = new UserDto(
                                    rs.getInt("user_id"),
                                    rs.getString("user_name"),
                                    rs.getString("password"),
                                    rs.getString("email"),
                                    rs.getInt("reputation"),
                                    rs.getBoolean("admin")
                                );
            usersDB.add(userDto);
        }

        // get users from repository
        List<UserDto> usersRepository = userService.getAllUsers();

        // check if same number of users in DB and repository
        assertEquals(usersDB.size(), usersRepository.size());

        // check if all users with same ID are equal
        for (UserDto userDB : usersDB) {
            for (UserDto userRepository : usersRepository) {
                if (userDB.getUserId().equals(userRepository.getUserId())) {
                    assertTrue(userDB.equals(userRepository));
                    break;
                }
            }
        }
    }

    @Test
    public void testDeleteUser() throws InvalidUserException {
        // user id exists -> deleted
        assertTrue(userService.deleteUser(existingUser.getUserId()));

        // user id does not exist -> throw exception
        try {
            userService.deleteUser(nonExistingUser.getUserId());
            fail("Expected InvalidUserException for userId " + nonExistingUser.getUserId());
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testLogin() throws InvalidLoginDataException {
        // local variables
        IdPw idPw;
        LoginDto loginDto;

        // email exists
        // correct password -> get LoginDto
        idPw = new IdPw(existingUser.getEmail(), existingUser.getPassword());
        loginDto = UserConverter.convertEntityToLoginDto(existingUser);
        assertTrue(loginDto.equals(userService.login(idPw)));

        idPw = new IdPw(existingAdminUser.getEmail(), existingAdminUser.getPassword());
        loginDto = UserConverter.convertEntityToLoginDto(existingAdminUser);
        assertTrue(loginDto.equals(userService.login(idPw)));

        // wrong password -> throw exception
        idPw = new IdPw(existingUser.getEmail(), "wrongPassword");
        try {
            userService.login(idPw);
            fail("Expected InvalidLoginDataException (wrong password) for email " + idPw.getUser() + ", password: " + idPw.getPw());
        } catch (InvalidLoginDataException e) {
            assertEquals(e.getMessage(), "Wrong password.");
        }

        // email does not exist -> throw exception
        idPw = new IdPw(nonExistingUser.getEmail(), nonExistingUser.getPassword());
        try {
            userService.login(idPw);
            fail("Expected InvalidLoginDataException (non-existing user) for email " + idPw.getUser() + ", password: " + idPw.getPw());
        } catch (InvalidLoginDataException e) {
            assertEquals(e.getMessage(), "User does not exist.");
        }
    }

    @Test
    public void testIncreaseUserReputation() throws InvalidUserException {
        // local variables
        Integer userId;

        // user exists
        // and reputation is 0 (can increase)
        userId = existingUser.getUserId();
        assertEquals(new Integer(1), userService.increaseUserReputation(userId));

        // and reputation is 5 (max, can't increase) for admin
        userId = existingAdminUser.getUserId();
        assertEquals(new Integer(5), userService.increaseUserReputation(userId));

        // user does not exist -> throw exception
        userId = nonExistingUser.getUserId();
        try {
            userService.increaseUserReputation(userId);
            fail("Expected InvalidUserException for userId " + userId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testDecreaseUserReputation() throws InvalidUserException {
        // local variables
        Integer userId;

        // user exists
        // and reputation is 0 (can decrease)
        userId = existingUser.getUserId();
        assertEquals(new Integer(-1), userService.decreaseUserReputation(userId));

        // and reputation is -5 (min, can't decrease)
        userId = existingUser.getUserId();
        existingUser.setReputation(-5);
        userRepository.save(existingUser);
        assertEquals(new Integer(-5), userService.decreaseUserReputation(userId));

        // user does not exist -> throw exception
        userId = nonExistingUser.getUserId();
        try {
            userService.increaseUserReputation(userId);
            fail("Expected InvalidUserException for userId " + userId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }
}
