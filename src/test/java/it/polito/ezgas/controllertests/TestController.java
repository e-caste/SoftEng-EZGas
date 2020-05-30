package it.polito.ezgas.controllertests;

import static it.polito.ezgas.utils.Constants.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ezgas.dto.UserDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class TestController {

    private final String url = "http://localhost:8080/";
    private final String apiPrefixUser = "user";
    private final String apiPrefixGasStation = "gasstation";

    private final Integer existingAdminUserId = 1, existingUserId = 2, nonExistingUserId = 1000;
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
    public void testGetUserById() throws IOException {
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
    public void testGetAllUsers() throws IOException {
        HttpUriRequest request = new HttpGet(url + apiPrefixUser + GET_ALL_USERS);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        UserDto[] users = mapper.readValue(json, UserDto[].class);
        assert users.length == 2;
    }

    @Test
    public void testSaveUser() throws IOException {
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
        UserDto addedUser = new UserDto(10, "newUser", "password", "new@new.new", 0, false);
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
    public void testDeleteUser() throws IOException {
        HttpDelete request = new HttpDelete(url + apiPrefixUser + DELETE_USER.replace("{userId}", String.valueOf(existingUserId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;
    }

    // GasStationController tests
}
