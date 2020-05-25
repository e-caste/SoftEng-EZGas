package it.polito.ezgas.controller;

import it.polito.ezgas.BootEZGasApplication;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

    private void separateTestsGraphically() {
        System.err.println("-----------------------------------------------------------------------------------------");
    }

    private String convertDtoToJSON(UserDto userDto) {
        String JSON =  "{" +
                       "\"userName\":\"" + userDto.getUserName() + "\"," +
                       "\"password\":\"" + userDto.getPassword() + "\"," +
                       "\"email\":\"" + userDto.getEmail() + "\"," +
                       "\"reputation\":" + userDto.getReputation() + ",";
        if (userDto.getUserId() != null) {
            JSON += "\"userId\":" + userDto.getUserId() + ",";
        }
        if (userDto.getAdmin() != null) {
            JSON += "\"admin\":\"" + userDto.getAdmin() + "\"";
        }
        JSON += "}";
        System.out.println(JSON);
        return JSON;
    }

    @Test
    public void testGetUserById() throws Exception {
        // user exists -> JSON returned
        mockMvc.perform(get(apiPrefix + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingAdminUserId)))
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"userName\":\"admin\",\"password\":\"admin\",\"email\":\"admin@ezgas.com\",\"reputation\":5,\"admin\":true}"))
            .andExpect(status().isOk())
            .andDo(print());
        separateTestsGraphically();

        // user exists -> JSON returned
        mockMvc.perform(get(apiPrefix + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingUserId)))
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"userId\":2,\"userName\":\"asd\",\"password\":\"asd\",\"email\":\"asd@asd.asd\",\"reputation\":0,\"admin\":false}"))
                .andExpect(status().isOk())
                .andDo(print());
        separateTestsGraphically();

        // user does not exist -> no JSON returned, no explicit error message nor status different from 200
        mockMvc.perform(get(apiPrefix + GET_USER_BY_ID.replace("{userId}", String.valueOf(nonExistingUserId)))
                .accept(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(
                        get(apiPrefix + GET_ALL_USERS)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("[{\"userId\":1,\"userName\":\"admin\",\"password\":\"admin\",\"email\":\"admin@ezgas.com\",\"reputation\":5,\"admin\":true},{\"userId\":2,\"userName\":\"asd\",\"password\":\"asd\",\"email\":\"asd@asd.asd\",\"reputation\":0,\"admin\":false}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    // run as last test, since it modifies the database
    @Test
    public void testSaveUser() throws Exception {
        // save new user
        nonExistingUser.setUserId(null);  // the userId of a non-existing user can be either null or the next expected value (3 in this case)
        nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);
        mockMvc.perform(post(apiPrefix + SAVE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertDtoToJSON(nonExistingUserDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("test"))
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.password").value("test"))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").value("test@test.test"))
                .andExpect(jsonPath("$.reputation").exists())
                .andExpect(jsonPath("$.reputation").value(0))
                .andExpect(jsonPath("$.admin").exists())
                .andExpect(jsonPath("$.admin").value(false))
                .andDo(print());
        separateTestsGraphically();

        // update existing user
        // we keep using nonExistingUser, but at this point it is an existing user in the database
        nonExistingUser.setUserId(3);
        nonExistingUser.setPassword("updatedPassword");
        nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);
        mockMvc.perform(post(apiPrefix + SAVE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertDtoToJSON(nonExistingUserDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.userName").exists())
                .andExpect(jsonPath("$.userName").value("test"))
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.password").value("updatedPassword"))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").value("test@test.test"))
                .andExpect(jsonPath("$.reputation").exists())
                .andExpect(jsonPath("$.reputation").value(0))
                .andExpect(jsonPath("$.admin").exists())
                .andExpect(jsonPath("$.admin").value(false))
                .andDo(print());
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
