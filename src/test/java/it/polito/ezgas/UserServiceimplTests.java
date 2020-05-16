package it.polito.ezgas;

import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.impl.UserServiceimpl;

import static org.junit.Assert.*;

import it.polito.ezgas.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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

    Integer existingUserId, nonExistingUserId;
    Boolean existingUserAdmin, nonExistingUserAdmin;
    private User existingUser, nonExistingUser;
    private UserDto existingUserDto, nonExistingUserDto;

    @Before  // run only once
    public void setUp() {

        // user with existing id in the database
        this.existingUser = new User("admin", "admin", "admin@ezgas.com", 5);
        this.existingUserId = 2;
        this.existingUserAdmin = true;
        existingUser.setUserId(this.existingUserId);
        existingUser.setAdmin(this.existingUserAdmin);
        this.existingUserDto = UserConverter.convertEntityToDto(existingUser);

        // user with non-existing id in the database
        this.nonExistingUser = new User("test", "test", "test", 0);
        this.nonExistingUserId = 1000;
        this.nonExistingUserAdmin = false;
        nonExistingUser.setUserId(this.nonExistingUserId);
        nonExistingUser.setAdmin(this.nonExistingUserAdmin);
        this.nonExistingUserDto = UserConverter.convertEntityToDto(nonExistingUser);

//        this.userService.getAllUsers().forEach(System.out::println);
    }

    @Test
    public void testGetUserById() throws InvalidUserException {
        assertTrue(this.existingUserDto.equals(this.userService.getUserById(this.existingUserId)));
        // assertThrows() - not available in this version of JUnit4
        try {
            this.userService.getUserById(this.nonExistingUserId);
            fail("Expected InvalidUserException for userId " + this.nonExistingUserId);
        } catch (InvalidUserException e) {
            assertEquals(e.getMessage(), "User not found");
        }
    }
}
