package it.polito.ezgas;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

import static org.junit.Assert.*;

import it.polito.ezgas.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// IMPORTANT NOTE: these tests can't run if the website is up and running,
//                 since they use the same port to connect to the DB
@RunWith(SpringRunner.class)
@SpringBootTest
//@Configuration
//@ComponentScan("it.polito.ezgas.repository")
public class UserServiceimplTests {

    // using UserService instead of UserServiceimpl since it's the interface
    @Autowired
    private UserService userService;

    Integer existingAdminUserId, existingUserId, nonExistingUserId;
    Boolean existingAdminUserAdmin, existingUserAdmin, nonExistingUserAdmin;
    private User existingAdminUser, existingUser, nonExistingUser;
    private UserDto existingAdminUserDto, existingUserDto, nonExistingUserDto;

    @Before  // run only once
    public void setUp() {

        // admin user with existing id in the database
        this.existingAdminUser = new User("admin", "admin", "admin@ezgas.com", 5);
        this.existingAdminUserId = 2;
        this.existingAdminUserAdmin = true;
        this.existingAdminUser.setUserId(this.existingAdminUserId);
        this.existingAdminUser.setAdmin(this.existingAdminUserAdmin);
        this.existingAdminUserDto = UserConverter.convertEntityToDto(this.existingAdminUser);

        // user with existing id in the database
        this.existingUser = new User("asd", "asd", "asd@asd.asd", 0);
        this.existingUserId = 1;
        this.existingUserAdmin = false;
        this.existingUser.setUserId(this.existingUserId);
        this.existingUser.setAdmin(this.existingUserAdmin);
        this.existingUserDto = UserConverter.convertEntityToDto(this.existingUser);

        // user with non-existing id in the database
        this.nonExistingUser = new User("test", "test", "test", 0);
        this.nonExistingUserId = 1000;
        this.nonExistingUserAdmin = false;
        this.nonExistingUser.setUserId(this.nonExistingUserId);
        this.nonExistingUser.setAdmin(this.nonExistingUserAdmin);
        this.nonExistingUserDto = UserConverter.convertEntityToDto(this.nonExistingUser);

//        this.userService.getAllUsers().forEach(System.out::println);
    }

    @After
    public void tearDown() {
        // TODO: reset database to initial state
    }

    @Test
    public void testGetUserById() throws InvalidUserException {
        assertTrue(this.existingAdminUserDto.equals(this.userService.getUserById(this.existingAdminUserId)));
        // assertThrows() - not available in this version of JUnit4
        try {
            this.userService.getUserById(this.nonExistingUserId);
            fail("Expected InvalidUserException for userId " + this.nonExistingUserId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testSaveUser() {
        // local copy of class variables
        User nonExistingUser = this.nonExistingUser;
        User existingUser = this.existingUser;
        UserDto nonExistingUserDto, existingUserDto;

        // user does not exist in database
        // and is admin -> reject (only one admin allowed)
        nonExistingUser.setAdmin(true);
        nonExistingUser.setUserId(null);
        nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);
        assertNull(this.userService.saveUser(nonExistingUserDto));

        // and is valid -> save new
        assertTrue(nonExistingUserDto.equalsIgnoreUserId(this.userService.saveUser(nonExistingUserDto)));

        // user exists in database
        // user id exists and email is same -> update user fields in database
        existingUser.setPassword("newPassword");
        existingUserDto = UserConverter.convertEntityToDto(existingUser);
        assertTrue(existingUserDto.equals(this.userService.saveUser(existingUserDto)));

        // user id exists but updates email -> reject (email must be invariable)
        existingUser.setEmail("qwe@qwe.qwe");
        existingUserDto = UserConverter.convertEntityToDto(existingUser);
        assertNull(this.userService.saveUser(existingUserDto));
    }

    @Test
    public void testGetAllUsers() {

    }

    @Test
    public void testDeleteUser() throws InvalidUserException {
        // local copy of class variables
        User existingUser = this.existingUser;
        User existingAdminUser = this.existingAdminUser;
        User nonExistingUser = this.nonExistingUser;

        // user id exists -> deleted
        assertTrue(this.userService.deleteUser(existingUser.getUserId()));

        // user id exists and is admin -> not deleted
        assertFalse(this.userService.deleteUser(existingAdminUser.getUserId()));

        // user id does not exist -> throw exception
        try {
            this.userService.deleteUser(nonExistingUser.getUserId());
            fail("Expected InvalidUserException for userId " + nonExistingUser.getUserId());
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testLogin() throws InvalidLoginDataException {
        // local copy of class variables
        User existingUser = this.existingUser;
        User existingAdminUser = this.existingAdminUser;
        User nonExistingUser = this.nonExistingUser;
        IdPw idPw;
        LoginDto loginDto;

        // email exists
        // correct password -> get LoginDto
        idPw = new IdPw(existingUser.getEmail(), existingUser.getPassword());
        loginDto = UserConverter.convertEntityToLoginDto(existingUser);
        assertEquals(loginDto, this.userService.login(idPw));

        idPw = new IdPw(existingAdminUser.getEmail(), existingAdminUser.getPassword());
        loginDto = UserConverter.convertEntityToLoginDto(existingAdminUser);
        assertEquals(loginDto, this.userService.login(idPw));

        // wrong password -> throw exception
        idPw = new IdPw(existingUser.getEmail(), "wrongPassword");
        try {
            this.userService.login(idPw);
            fail("Expected InvalidLoginDataException (wrong password) for email " + idPw.getUser() + ", password: " + idPw.getPw());
        } catch (InvalidLoginDataException e) {
            assertEquals(e.getMessage(), "Wrong password.");
        }

        // email does not exist -> throw exception
        idPw = new IdPw(nonExistingUser.getEmail(), nonExistingUser.getPassword());
        try {
            this.userService.login(idPw);
            fail("Expected InvalidLoginDataException (non-existing user) for email " + idPw.getUser() + ", password: " + idPw.getPw());
        } catch (InvalidLoginDataException e) {
            assertEquals(e.getMessage(), "User does not exist.");
        }
    }

    @Test
    public void testIncreaseUserReputation() throws InvalidUserException {
        // local copy of class variables
        User existingUser = this.existingUser;
        User existingAdminUser = this.existingAdminUser;
        User nonExistingUser = this.nonExistingUser;
        Integer userId;

        // user exists
        // and reputation is 0 (can increase)
        userId = existingUser.getUserId();
        assertEquals(new Integer(1), this.userService.increaseUserReputation(userId));

        // and reputation is 5 (max, can't increase)
        userId = existingAdminUser.getUserId();
        assertEquals(new Integer(5), this.userService.increaseUserReputation(userId));

        // user does not exist -> throw exception
        userId = nonExistingUser.getUserId();
        try {
            this.userService.increaseUserReputation(userId);
            fail("Expected InvalidUserException for userId " + userId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }

    @Test
    public void testDecreaseUserReputation() throws InvalidUserException {
        // local copy of class variables
        User existingUser = this.existingUser;
        User existingAdminUser = this.existingAdminUser;
        User nonExistingUser = this.nonExistingUser;
        Integer userId;

        // user exists
        // and reputation is 0 (can decrease)
        userId = existingUser.getUserId();
        assertEquals(new Integer(-1), this.userService.decreaseUserReputation(userId));

        // and reputation is -5 (min, can't decrease)
        userId = existingUser.getUserId();
        existingUser.setReputation(-5);
        assertEquals(new Integer(-5), this.userService.decreaseUserReputation(userId));

        // user does not exist -> throw exception
        userId = nonExistingUser.getUserId();
        try {
            this.userService.increaseUserReputation(userId);
            fail("Expected InvalidUserException for userId " + userId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }
}
