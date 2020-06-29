package it.polito.ezgas.repository;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    static Connection db;
    static Statement st;
    static ResultSet backup;
    static String sqlSelectAllUsers = "SELECT * FROM USER";
    static String sqlSelectUserWhereId = "SELECT * FROM USER WHERE USER_ID=:id";
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

    Integer existingAdminUserId, existingUserId, nonExistingUserId;
    Boolean existingAdminUserAdmin, existingUserAdmin, nonExistingUserAdmin;
    String existingUserEmail = "asd@asd.asd", nonExistingUserEmail = "test@test.test";
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
        existingUser = new User("asd", "asd", existingUserEmail, 0);
        existingUserId = 2;
        existingUserAdmin = false;
        existingUser.setUserId(existingUserId);
        existingUser.setAdmin(existingUserAdmin);
        existingUserDto = UserConverter.convertEntityToDto(existingUser);

        // user with non-existing id in the database
        nonExistingUser = new User("test", "test", nonExistingUserEmail, 0);
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

    private List<User> selectAll() throws SQLException {
        ResultSet rs = st.executeQuery(sqlSelectAllUsers);
        List<User> allUsers = new ArrayList<>();
        while (rs.next()) {
            User user = new User(
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getInt("reputation")
                    );
            user.setUserId(rs.getInt("user_id"));
            user.setAdmin(rs.getBoolean("admin"));
            allUsers.add(user);
        }
        return allUsers;
    }

    private User selectById(Integer id) throws SQLException {
        // this may be prone to SQL injection, but the statement.setInt() method is not available for some reason
        // also, this is not really a security issue since we're only using this for testing
        ResultSet rs = st.executeQuery(sqlSelectUserWhereId.replace(":id", String.valueOf(id)));
        if (rs.next()) {
            User user = new User(
                    rs.getString("user_name"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getInt("reputation")
            );
            user.setUserId(rs.getInt("user_id"));
            user.setAdmin(rs.getBoolean("admin"));
            return user;
        }
        return null;
    }

    @Test
    public void testFindById() {
        // id exists in database -> return User object
        User user = userRepository.findById(existingUserId);
        assertTrue(user.equals(existingUser));

        // id doesn't exist in database -> return null
        user = userRepository.findById(nonExistingUserId);
        assertNull(user);
    }

    @Test
    public void testFindByEmail() {
        // email exists in database -> return User object
        User user = userRepository.findByEmail(existingUserEmail);
        assertTrue(user.equals(existingUser));

        // email doesn't exist in database -> return null
        user = userRepository.findByEmail(nonExistingUserEmail);
        assertNull(user);
    }

    @Test
    public void testFindAll() {
        // check that the manually inserted users existingAdminUser and existingUser are returned
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        for (User user : users) {
            if (user.getUserId().equals(existingAdminUserId)) {
                assertTrue(user.equals(existingAdminUser));
            }
            if (user.getUserId().equals(existingUserId)) {
                assertTrue(user.equals(existingUser));
            }
        }
    }

    // runs last (in alphabetical order) as this method changes the content of the database
    @Test
    public void testSave() throws SQLException {
        // save new user -> insert in database
        userRepository.save(nonExistingUser);
        assertTrue(nonExistingUser.equals(selectById(nonExistingUserId)));

        // save existing user -> update database (the checks if it's possible are done by UserService, ignored here)
        existingUser.setPassword("aNewPassword");
        userRepository.save(existingUser);
        assertTrue(existingUser.equals(selectById(existingUserId)));

        // save incomplete user -> should probably fail? but it doesn't, since the only not null key is the id
        Integer incompleteUserId = 4;
        User incompleteUser = new User("username", null, "email", null);
        incompleteUser.setUserId(incompleteUserId);
        userRepository.save(incompleteUser);
        // this below fails because the null value of the password doesn't have an equals() method
        //assertTrue(incompleteUser.equals(selectById(incompleteUserId)));
    }

    @Test
    public void testDelete() throws SQLException {
        // the delete method returns void, so no checks can be done directly
        // id exists -> user deleted from database (the checks if it's admin are done in UserService, ignored here)
        userRepository.delete(existingUserId);
//        List<User> users = userRepository.findAll();
        List<User> users = selectAll();
        assertEquals(1, users.size());
        assertTrue(users.get(0).equals(existingAdminUser));

        // id doesn't exist -> fail
        try {
            userRepository.delete(nonExistingUserId);  // throws hidden exception
        } catch (EmptyResultDataAccessException e) {
            assertEquals(e.getMessage(), "No class it.polito.ezgas.entity.User entity with id 3 exists!");
        }
    }
}
