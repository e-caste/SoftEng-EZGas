package it.polito.ezgas.controllertests;

import static it.polito.ezgas.utils.Constants.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class TestController {

    static String url = "http://localhost:8080/";
    static String apiPrefixUser = "user/";
    static String apiPrefixGasStation = "gasstation/";

    static Integer existingAdminUserId = 1, existingUserId = 2, nonExistingUserId = 1000;

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
    }

    // GasStationController tests
}
