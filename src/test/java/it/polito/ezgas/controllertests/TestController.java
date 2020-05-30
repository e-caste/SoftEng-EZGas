package it.polito.ezgas.controllertests;

import static it.polito.ezgas.utils.Constants.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;

public class TestController {

    static String url = "http://localhost:8080/";
    static String apiPrefixUser = "user/";
    static String apiPrefixGasStation = "gasstation/";

    static Integer existingAdminUserId = 1, existingUserId = 2, nonExistingUserId = 1000;

    // UserController tests
    @Test
    public void testGetUserById() throws IOException {
        // user exists and is admin
        HttpUriRequest request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingAdminUserId)));
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // user exists and is not admin
        request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(existingUserId)));
        response = HttpClientBuilder.create().build().execute(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // user does not exist
        request = new HttpGet(url + apiPrefixUser + GET_USER_BY_ID.replace("{userId}", String.valueOf(nonExistingUserId)));
        response = HttpClientBuilder.create().build().execute(request);
        assert response.getStatusLine().getStatusCode() == 404;
    }


    // GasStationController tests
}
