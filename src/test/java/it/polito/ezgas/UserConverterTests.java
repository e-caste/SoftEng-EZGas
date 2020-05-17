package it.polito.ezgas;

import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTests {

    User user;
    UserDto userDto;
    LoginDto loginDto;

    // constants to initialize variables in setUp method
    String username = "username", password = "password", email = "test@test.test", token = "token";
    Integer reputation = 0, userId = 10;
    Boolean admin = false;

    @Before
    public void setUp() {
        user = new User(username, password, email, reputation);
        user.setAdmin(admin);
        user.setUserId(userId);

        userDto = new UserDto(userId, username, password, email, reputation, admin);

        loginDto = new LoginDto(userId, username, token, email, reputation);
        loginDto.setAdmin(admin);
    }

    @Test
    public void testConvertEntityToDto() {

    }

    @Test
    public void testConvertDtoToEntity() {

    }

    @Test
    public void testConvertEntityToLoginDto() {

    }
}
