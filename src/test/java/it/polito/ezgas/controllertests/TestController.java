package it.polito.ezgas.controllertests;

import static it.polito.ezgas.utils.Constants.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestController {

    private final String url = "http://localhost:8080/";
    private final String apiPrefixUser = "user";
    private final String apiPrefixGasStation = "gasstation";

    private final Integer existingAdminUserId = 1,
                          existingUserId = 2,
                          nonExistingUserId = 1000,
                          addedUserId = 10;
    private final String newUserJson = "{\"userId\":10,\"userName\":\"newUser\",\"password\":\"password\",\"email\":\"new@new.new\",\"reputation\":0,\"admin\":false}";
    private final String existingModifiedUserJson = "{\"userId\":2,\"userName\":\"asd\",\"password\":\"newPassword\",\"email\":\"asd@asd.asd\",\"reputation\":0,\"admin\":false}";

    private HttpResponse getResponseFromRequest(HttpUriRequest request) throws IOException {
        return HttpClientBuilder.create().build().execute(request);
    }

    private String getJsonFromResponse(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

    private ObjectMapper getMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // UserController tests
    @Test
    public void test0GetUserById() throws IOException {
        // user exists and is admin
        HttpUriRequest request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingAdminUserId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // user exists and is not admin
        request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // user does not exist
        request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(nonExistingUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 404;
    }

    @Test
    public void test1GetAllUsers() throws IOException {
        HttpUriRequest request = new HttpGet(url + apiPrefixUser + GET_ALL_USERS);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        UserDto[] users = mapper.readValue(json, UserDto[].class);
        assert users.length == 2;
    }

    @Test
    public void test2SaveUser() throws IOException {
        // save new user
        HttpPost request = new HttpPost(url + apiPrefixUser + SAVE_USER);
        StringEntity params = new StringEntity(newUserJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        UserDto user = mapper.readValue(json, UserDto.class);
        UserDto addedUser = new UserDto(10, "newUser", "password", "new@new.new", -5, false);
        assert user.equals(addedUser);

        // save existing user
        request = new HttpPost(url + apiPrefixUser + SAVE_USER);
        params = new StringEntity(existingModifiedUserJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        user = mapper.readValue(json, UserDto.class);
        addedUser = new UserDto(2, "asd", "newPassword", "asdasd.asd", 0, false);
        assert user.equals(addedUser);
    }

    @Test
    public void test6DeleteUser() throws IOException {
        // existing user
        HttpDelete request = new HttpDelete(url + apiPrefixUser + DELETE_USER.replace("{userId}", String.valueOf(existingUserId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // non-existing user
        request = new HttpDelete(url + apiPrefixUser + DELETE_USER.replace("{userId}", String.valueOf(nonExistingUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 404;
    }

    @Test
    public void test3IncreaseUserReputation() throws IOException {
        // increase from 0 -> return 1
        HttpPost request = new HttpPost(url + apiPrefixUser + INCREASE_REPUTATION.replace("{userId}", String.valueOf(existingUserId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        Integer updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == 1;

        // increase from 5 -> return 5
        request = new HttpPost(url + apiPrefixUser + INCREASE_REPUTATION.replace("{userId}", String.valueOf(existingAdminUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == 5;

        // user doesn't exist -> return 0
        request = new HttpPost(url + apiPrefixUser + INCREASE_REPUTATION.replace("{userId}", String.valueOf(nonExistingUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == 0;
    }

    @Test
    public void test4DecreaseUserReputation() throws IOException {
        // decrease from 1 -> return 0
        HttpPost request = new HttpPost(url + apiPrefixUser + DECREASE_REPUTATION.replace("{userId}", String.valueOf(existingUserId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        Integer updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == 0;

        // decrease from -5 -> return -5
        request = new HttpPost(url + apiPrefixUser + DECREASE_REPUTATION.replace("{userId}", String.valueOf(addedUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == -5;

        // user doesn't exist -> return 0
        request = new HttpPost(url + apiPrefixUser + DECREASE_REPUTATION.replace("{userId}", String.valueOf(nonExistingUserId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        updatedReputation = mapper.readValue(json, Integer.class);
        assert updatedReputation == 0;
    }

    @Test
    public void test5Login() throws IOException {
        // login existing admin user
        HttpPost request = new HttpPost(url + apiPrefixUser + LOGIN);
        StringEntity params = new StringEntity("{\"user\":\"admin\",\"pw\":\"admin\"}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        LoginDto loginDto = mapper.readValue(json, LoginDto.class);
        LoginDto adminLoginDto = new LoginDto(1, "admin", "token", "admin@ezgas.com", 5);
        assert loginDto.equals(adminLoginDto);

        // login existing non-admin user
        request = new HttpPost(url + apiPrefixUser + LOGIN);
        params = new StringEntity("{\"user\":\"asd\",\"pw\":\"asd\"}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        loginDto = mapper.readValue(json, LoginDto.class);
        LoginDto userLoginDto = new LoginDto(2, "asd", "token", "asd@asd.asd", 0);
        assert loginDto.equals(userLoginDto);

        // login non-existing user
        request = new HttpPost(url + apiPrefixUser + LOGIN);
        params = new StringEntity("{\"user\":\"nonExisting\",\"pw\":\"nonExisting\"}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 404;
    }

    // GasStationController tests
}
