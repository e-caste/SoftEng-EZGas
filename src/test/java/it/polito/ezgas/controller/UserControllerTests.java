package it.polito.ezgas.controller;

import it.polito.ezgas.BootEZGasApplication;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static it.polito.ezgas.utils.Constants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BootEZGasApplication.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTests {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

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

    static String apiPrefix = "/user";

    Integer existingAdminUserId, existingUserId, nonExistingUserId;
    Boolean existingAdminUserAdmin, existingUserAdmin, nonExistingUserAdmin;
    private User existingAdminUser, existingUser, nonExistingUser;
    private UserDto existingAdminUserDto, existingUserDto, nonExistingUserDto;

    @Before  // run before each test
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
    }

    @PostConstruct
    @BeforeClass  // run only once
    public static void setUpDatabase() throws SQLException {
        db = DriverManager.getConnection("jdbc:h2:./data/test", "sa", "password");
        st = db.createStatement();
        st.executeUpdate(sqlDropUserTable);
        st.executeUpdate(sqlCreateUserTable);
        for (String sql : sqlInsertUsers) {
            st.executeUpdate(sql);
        }
    }

    @AfterClass  // run only once
    public static void tearDown() throws SQLException {
        st.close();
        db.close();
    }

    @Test
    public void testGetUserById() {

    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get(apiPrefix + GET_ALL_USERS).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    public void testSaveUser() {

    }

    @Test
    public void testDeleteUser() {

    }

    @Test
    public void testIncreaseUserReputation() {

    }

    @Test
    public void testDecreaseUserReputation() {

    }

    @Test
    public void testLogin() {

    }
}
