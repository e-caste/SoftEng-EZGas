package it.polito.ezgas.controller;

import it.polito.ezgas.BootEZGasApplication;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.entity.GasStation;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
public class GasStationControllerTests {
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    static Connection db;
    static Statement st;
    static ResultSet backup;
    static String sqlSelectAllGSs = "SELECT * FROM GAS_STATION";
    static String sqlSelectGSbyCarSharing = "SELECT * FROM GAS_STATION WHERE CAR_SHARING='bah';";
    static String sqlSelectGSbyGasType = "SELECT * FROM GAS_STATION WHERE HAS_SUPER=TRUE;";

    static String sqlDropGSTable = "DROP TABLE IF EXISTS GAS_STATION";
    static String sqlCreateGSTable = "CREATE TABLE GAS_STATION " +
            "(gas_station_id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            "car_sharing VARCHAR(255), " +
            "diesel_price DOUBLE, " +
            "gas_price DOUBLE, " +
            "gas_station_address VARCHAR(255), " +
            "gas_station_name VARCHAR(255), " +
            "has_diesel BOOLEAN, " +
            "has_gas BOOLEAN, " +
            "has_methane BOOLEAN, " +
            "has_super BOOLEAN, " +
            "has_super_plus BOOLEAN, " +
            "has_premium_diesel BOOLEAN, " +
            "lat DOUBLE, " +
            "lon DOUBLE, " +
            "methane_price DOUBLE, " +
            "report_dependability DOUBLE, " +
            "report_timestamp VARCHAR(255), " +
            "report_user INTEGER, " +
            "super_price DOUBLE, " +
            "super_plus_price DOUBLE, " +
            "premium_diesel_price DOUBLE, " +
            "user_id INTEGER)";


    static List<String> sqlInsertGSs = Arrays.asList(
            //id|car|dies_pr|gas_pr|gas_station_address|station_name|                                 has_die|has_g|has_met|has_s|has_s_p| has_p_d|	lat	|	lon		|met_pr|  r_dep|time|               r_user|s_pr|s_p_pr| p_d_pr|user_id
            "INSERT INTO GAS_STATION VALUES (1, 'Enjoy', 1.375, 1.753, 'via Olanda, 12, Torino', 'Esso',  TRUE, TRUE, FALSE,  TRUE,  FALSE,FALSE, 45.048903, 7.659812, 0.0,  		0, '05-24-2020', -1,  1.864, 0.0,  0.0, NULL)",
            "INSERT INTO GAS_STATION VALUES (2, 'Enjoy', 1.431, 1.658, 'via Spagna, 32, Torino', 'Eni', TRUE, TRUE, FALSE,  FALSE,  FALSE,FALSE, 45.048903, 7.659812, 0.0, 		0,  '05-23-2020', -1, 0.0, 0.0, 0.0,   NULL)"

    );
    static String apiPrefix = "/gasstation";
    private GasStation GS1_existing, GS10_nonExisting;
    private GasStationDto GS1dto, GS10dto;
    Integer GS1_id, GS10_id;

    static int radius = 5;  // kilometers

    @Before
    public void setUp() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //Gas station with existing id in the database
        GS1_existing = new GasStation("Esso", "via Olanda, 12, Torino", true, true, false, true, false, false, "Enjoy", 45.048903, 7.659812, 1.375, 1.864, 0.0, 1.753, 0.0,0.0, -1, "05-24-2020", 0);
        GS1_id = 1;
        GS1_existing.setGasStationId(GS1_id);
        GS1dto = GasStationConverter.convertEntityToDto(GS1_existing);

        GS10_nonExisting = new GasStation("Repsol", "via Olanda, 12, Torino", true, false, false, false, false, false,"Enjoy", 45.048903, 7.659812, 1.375, 0.0, 0.0, 0.0, 0.0, 0.0,-1, "05-26-2020", 0);
        GS10_id = 10;
        GS10_nonExisting.setGasStationId(GS10_id);
        GS10dto = GasStationConverter.convertEntityToDto(GS10_nonExisting);


        // reset database
        st.executeUpdate(sqlDropGSTable);
        st.executeUpdate(sqlCreateGSTable);
        for (String sql : sqlInsertGSs) {
            st.executeUpdate(sql);
        }

    }

    @PostConstruct
    @BeforeClass  // run only once
    public static void setUpDatabase() throws SQLException {
        db = DriverManager.getConnection("jdbc:h2:./data/test", "sa", "password");
        st = db.createStatement();
    }

    @AfterClass  // run only once
    public static void tearDown() throws SQLException {
        st.close();
        db.close();
    }

    private String convertGasStationDtoToJSON(GasStationDto gasStationDto) {
        String JSON = "{" +
                "\"gasStationName\":\"" + gasStationDto.getGasStationName() + "\"," +
                "\"gasStationAddress\":\"" + gasStationDto.getGasStationAddress() + "\"," +
                "\"hasDiesel\":" + gasStationDto.getHasDiesel() + "," +
                "\"hasSuper\":" + gasStationDto.getHasSuper() + "," +
                "\"hasSuperPlus\":" + gasStationDto.getHasSuperPlus() + "," +
                "\"hasGas\":" + gasStationDto.getHasGas() + "," +
                "\"hasMethane\":" + gasStationDto.getHasMethane() + "," +
                "\"hasPremiumDiesel\":" + gasStationDto.getHasPremiumDiesel() + "," +

                "\"carSharing\":\"" + gasStationDto.getCarSharing() + "\"," +
                "\"lat\":" + gasStationDto.getLat() + "," +
                "\"lon\":" + gasStationDto.getLon() + "," +
                "\"dieselPrice\":" + gasStationDto.getDieselPrice() + "," +
                "\"superPrice\":" + gasStationDto.getSuperPrice() + "," +
                "\"superPlusPrice\":" + gasStationDto.getSuperPlusPrice() + "," +
                "\"gasPrice\":" + gasStationDto.getGasPrice() + "," +
                "\"methanePrice\":" + gasStationDto.getMethanePrice() + "," +
                "\"premiumDieselPrice\":" + gasStationDto.getPremiumDieselPrice() + "," +
                "\"reportUser\":" + gasStationDto.getReportUser() + "," +
                "\"userDto\":" + gasStationDto.getUserDto() + "," +
                "\"reportTimestamp\":\"" + gasStationDto.getReportTimestamp() + "\"," +
                "\"reportDependability\":" + gasStationDto.getReportDependability() + ",";
                // we are currently not using the list of priceReportDtos, and anyway it is not JSON-serializable
//                "\"priceReportDtos\":\"" + gasStationDto.getPriceReportDtos() + "\",";

        if (gasStationDto.getGasStationId() != null) {
            JSON += "\"gasStationId\":" + gasStationDto.getGasStationId();
        }

        JSON += "}";
        System.out.println(JSON);
        return JSON;
    }

    private String convertPriceReportDtoToJSON(PriceReportDto priceReportDto) {
        String JSON = "{" +
                "\"gasStationId\":" + priceReportDto.getGasStationId() + "," +
                "\"dieselPrice\":" + priceReportDto.getDieselPrice() + "," +
                "\"superPrice\":" + priceReportDto.getSuperPrice() + "," +
                "\"superPlusPrice\":" + priceReportDto.getSuperPlusPrice() + "," +
                "\"gasPrice\":" + priceReportDto.getGasPrice() + "," +
                "\"methanePrice\":" + priceReportDto.getMethanePrice() + "," +
                "\"premiumDieselPrice\":" + priceReportDto.getPremiumDieselPrice() + "," +
                "\"userId\":" + priceReportDto.getUserId() +
                "}";
        System.out.println(JSON);
        return JSON;
    }

    private void separateTestsGraphically() {
        System.err.println("-----------------------------------------------------------------------------------------");
    }

    @Test
    public void test_getGasStationById() throws Exception {
        //existing gas station
        mockMvc.perform(get(apiPrefix + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(GS1_id)))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andDo(print());


        //not existing gas station
        mockMvc.perform(get(apiPrefix + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(GS10_id)))
                .accept(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void test_getAllGasStations() throws Exception {
        mockMvc.perform(get(apiPrefix + GET_ALL_GASSTATIONS)
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}" +
                        "{\"gasStationId\":2,\"gasStationName\":\"Eni\",\"gasStationAddress\":\"via Spagna, 32, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.431,\"superPrice\":1.854,\"superPlusPrice\":0.0,\"gasPrice\":1.658,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-23-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

 @Test
    public void test_saveGasStation() throws Exception {

        // save new gasStation
        GS10_nonExisting.setGasStationId(GS10_id);  // the gasStationId of a non-existing GS can be either null or a new value
        GS10dto = GasStationConverter.convertEntityToDto(GS10_nonExisting);
        mockMvc.perform(post(apiPrefix + SAVE_GASSTATION)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(convertGasStationDtoToJSON(GS10dto))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        separateTestsGraphically();

        // update existing gasStation
        // we keep using GS10_nonExisting, but at this point it is an existing gasStation in the database
        GS10_nonExisting.setGasStationId(10);
        GS10_nonExisting.setGasStationName("newName");
        GS10dto = GasStationConverter.convertEntityToDto(GS10_nonExisting);
        mockMvc.perform(post(apiPrefix + SAVE_GASSTATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertGasStationDtoToJSON(GS10dto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void test_deleteGasStation() throws Exception {

        //deleting an existing gasStation
        mockMvc.perform(delete(apiPrefix + DELETE_GASSTATION.replace("{gasStationId}",String.valueOf(GS1_id)))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").value(true))
                .andDo(print());

        separateTestsGraphically();
        //deleting a non existing gasStation
        mockMvc.perform(delete(apiPrefix + DELETE_GASSTATION.replace("{gasStationId}", String.valueOf(GS10_id)))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$").value(false))
                .andDo(print());



    }

    @Test
    public void test_getGasStationsByGasolineType() throws Exception {

        mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_BY_GASOLINETYPE.replace("{gasolinetype}", String.valueOf("Super")))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());
    }

    @Test
    public void test_getGasStationsByProximity() throws Exception {
        //test with correct values for latitude and longitude
       /* mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_BY_PROXIMITY.replace("{myLat}",String.valueOf("45.048903"))
                .replace("{myLon}",String.valueOf("7.659812")))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());*/

        separateTestsGraphically();
        //test with invalid values for latitude and longitude
        mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_BY_PROXIMITY.replace("{myLat}","-91")
                .replace("{myLon}","45")
                .replace("{myRadius}","5"))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void test_getGasStationsWithCoordinates() throws Exception {
        mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","45.048903")
                .replace("{myLon}","7.659812")
                .replace("{myRadius}", "5")
                .replace("{gasolineType}","Super").replace("{carSharing}","Enjoy"))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(print());

        separateTestsGraphically();
        //invalid car sharing
        mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","45.048903")
                .replace("{myLon}","7.659812")
                .replace("{myRadius}", "5")
                .replace("{gasolineType}","Super").replace("{carSharing}","boh"))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());

        separateTestsGraphically();
   //invalid coordinates
        mockMvc.perform(get(apiPrefix + GET_GASSTATIONS_WITH_COORDINATES.replace("{myLat}","91")
                .replace("{myLon}","45")
                .replace("{myRadius}", "5")
                .replace("{gasolineType}","Super").replace("{carSharing}","Enjoy"))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":true,\"hasSuperPlus\":false,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"05-24-2020\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void test_setGasStationReport() throws Exception {
        PriceReportDto validPriceReportDto = new PriceReportDto(
                1,
                1.1,
                1.2,
                1.3,
                1.4,
                1.5,
                1.6,
                1
        );
        PriceReportDto invalidPricePriceReportDto = new PriceReportDto(
                1,
                -1.0,
                1.2,
                1.3,
                1.4,
                1.5,
                1.6,
                1
        );
        PriceReportDto invalidGasStationIdPriceReportDto = new PriceReportDto(
                1000,
                1.1,
                1.2,
                1.3,
                1.4,
                1.5,
                1.6,
                1
        );
        PriceReportDto invalidUserIdPriceReportDto = new PriceReportDto(
                1,
                1.1,
                1.2,
                1.3,
                1.4,
                1.5,
                1.6,
                1000
        );

        // priceReportDto is valid
        System.out.println("\nExpected message: none");
        mockMvc.perform(post(apiPrefix + SET_GASSTATION_REPORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertPriceReportDtoToJSON(validPriceReportDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        separateTestsGraphically();

        // the following tests can only check if they receive a response, since the GasStationController.setReport method returns void
        // to check if they're working correctly, simply inspect the output and check that the printed exception message corresponds to the right test case
        // priceReportDto has invalid (negative) price
        System.out.println("\nExpected message: Wrong price");
        mockMvc.perform(post(apiPrefix + SET_GASSTATION_REPORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertPriceReportDtoToJSON(invalidPricePriceReportDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        separateTestsGraphically();

        // priceReportDto has non-existing gasStationId
        System.out.println("\nExpected message: Gas Station not found");
        mockMvc.perform(post(apiPrefix + SET_GASSTATION_REPORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertPriceReportDtoToJSON(invalidGasStationIdPriceReportDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        separateTestsGraphically();

        // priceReportDto has non-existing userId
        System.out.println("\nExpected message: User not found");
        mockMvc.perform(post(apiPrefix + SET_GASSTATION_REPORT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertPriceReportDtoToJSON(invalidUserIdPriceReportDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}



