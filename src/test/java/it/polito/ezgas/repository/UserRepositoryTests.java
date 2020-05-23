package it.polito.ezgas.repository;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

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
    public void testFindById() {

    }

    @Test
    public void testFindByEmail() {

    }

    @Test
    public void testFindAll() {

    }

    @Test
    public void testSave() {

    }

    @Test
    public void testDelete() {

    }
}
