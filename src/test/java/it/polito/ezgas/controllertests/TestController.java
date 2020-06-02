package it.polito.ezgas.controllertests;

import static it.polito.ezgas.utils.Constants.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;


// TODO: in order to run these tests rename the testController.mv.db in memo.mv.db
//We have also done the controllerTests in MockMvc, their are in the package test/java/it.polito.ezgas/controller

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


    private final Integer existingGasStationId = 1,
                   nonExistingGasStationId = 55;
    private final String newGasStationJson = "{\"gasStationId\":50,\"gasStationName\":\"Eni\",\"gasStationAddress\":\"via Spagna, 32, Torino\",\"hasDiesel\":true,\"hasSuper\":false,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.431,\"superPrice\":0.0,\"superPlusPrice\":0.0,\"gasPrice\":1.658,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"2020-05-31 00:12:09\",\"reportDependability\":0}";
    private final String existingModifiedGasStationJson = "{\"gasStationId\":1,\"gasStationName\":\"Eni1234\",\"gasStationAddress\":\"Piazza Gian Lorenzo Bernini Turin Piemont Italy \",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.0757003,\"lon\":7.6562299,\"dieselPrice\":1.400,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"2020-05-24 19:54:07\",\"reportDependability\":0}";


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

    @Test
    public void test6GetGasStationById() throws IOException {
        // gas station exists
        HttpUriRequest request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(existingGasStationId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;



        // gas station does not exist
       request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(nonExistingGasStationId)));
       response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;
    }

    @Test
    public void test7GetAllGasStations() throws IOException {
        HttpUriRequest request = new HttpGet(url + apiPrefixGasStation + GET_ALL_GASSTATIONS);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;


        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        GasStationDto[] gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 2;
    }




    @Test
    public void test8SaveGasStation() throws IOException {
        // save new gas station
        HttpPost request = new HttpPost(url + apiPrefixGasStation + SAVE_GASSTATION);
        StringEntity params = new StringEntity(newGasStationJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;



        // update existing GasStation
        request = new HttpPost(url + apiPrefixGasStation + SAVE_GASSTATION);
        params = new StringEntity(existingModifiedGasStationJson);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;


    }

    @Test
    public void test9DeleteGasStation() throws IOException {
        // existing gasStation
        HttpDelete request = new HttpDelete(url + apiPrefixGasStation + DELETE_GASSTATION.replace("{gasStationId}", String.valueOf(existingGasStationId)));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        // non-existing gasStation
        request = new HttpDelete(url + apiPrefixGasStation + DELETE_GASSTATION.replace("{gasStationId}", String.valueOf(nonExistingGasStationId)));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;


        //reAdding the deleted gasStation for the following tests
        HttpPost request1 = new HttpPost(url + apiPrefixGasStation + SAVE_GASSTATION);
        StringEntity params = new StringEntity(existingModifiedGasStationJson);
        request1.addHeader("content-type", "application/json");
        request1.setEntity(params);
        response = getResponseFromRequest(request1);
        assert response.getStatusLine().getStatusCode() == 200;

    }



    @Test
    public void test10GetGasStationsByGasolineType() throws IOException {

        HttpGet request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATIONS_BY_GASOLINETYPE.replace("{gasolinetype}", String.valueOf("Diesel")));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        GasStationDto[] gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 2;
    }


    @Test
    public void test11GetGasStationsByProximity()throws IOException{
        //invalid value of coordinates
        HttpGet request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATIONS_BY_PROXIMITY.replace("{myLat}","-91")
                .replace("{myLon}","45"));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        GasStationDto[] gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 0;

    }


    @Test
    public void test12GetGasStationsWithCoordinates()throws IOException{
        //valid values
        HttpGet request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","45.0685187")
                .replace("{myLon}","7.66279")
                .replace("{gasolineType}","Diesel").replace("{carSharing}","Car2Go"));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        String json = getJsonFromResponse(response);
        ObjectMapper mapper = getMapper();
        GasStationDto[] gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 1;

    //invalid car sharing
        request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","45.048903")
                .replace("{myLon}","7.659812")
                .replace("{gasolineType}","Super").replace("{carSharing}","Enjoyyyy"));
        //response = getResponseFromRequest(request);
        response = HttpClientBuilder.create().build().execute(request);
        assert (response.getStatusLine().getStatusCode() == 200);

        json = getJsonFromResponse(response);
        mapper = getMapper();
        gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 0;


        //invalid coordinates
        request = new HttpGet(url + apiPrefixGasStation + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","91")
                .replace("{myLon}","45")
                .replace("{gasolineType}","Super").replace("{carSharing}","Enjoy"));
        response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

        json = getJsonFromResponse(response);
        mapper = getMapper();
        gasStations = mapper.readValue(json, GasStationDto[].class);
        assert gasStations.length == 0;

    }



    @Test
    public void test13SetGasStationReport() throws IOException {

        HttpPost request = new HttpPost(url + apiPrefixGasStation + SET_GASSTATION_REPORT.replace("{gasStationId}",String.valueOf(existingGasStationId))
                .replace("{dieselPrice}","1.452").replace("{superPrice}","1.764")
                .replace("{superPlusPrice}", "0").replace("{gasPrice}","1.812")
                .replace("{methanePrice}", "0").replace("{userId}", "1"));
        HttpResponse response = getResponseFromRequest(request);
        assert response.getStatusLine().getStatusCode() == 200;

    }




}
