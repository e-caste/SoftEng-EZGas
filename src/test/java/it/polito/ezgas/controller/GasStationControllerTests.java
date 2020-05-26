package it.polito.ezgas.controller;

import it.polito.ezgas.BootEZGasApplication;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
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
public class GasStationControllerTests {
   MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;
    static Connection db;
    static Statement st;
    static ResultSet backup;
    static String sqlSelectAllGSs = "SELECT * FROM GAS_STATION";
    static String sqlSelectGSbyCarSharing = "SELECT * FROM GAS_STATION WHERE CAR_SHARING='bah';";
    static String sqlSelectGSbyGasType = "SELECT * FROM GAS_STATION WHERE HAS_DIESEL=TRUE;";

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
            "lat DOUBLE, " +
            "lon DOUBLE, " +
            "methane_price DOUBLE, " +
            "report_dependability DOUBLE, " +
            "report_timestamp VARCHAR(255), " +
            "report_user INTEGER, " +
            "super_price DOUBLE, " +
            "super_plus_price DOUBLE, " +
            "user_id INTEGER)";





    static List<String> sqlInsertGSs = Arrays.asList(
            //id|car|dies_pr|gas_pr|gas_station_address|station_name|                                 has_die|has_g|has_met|has_s|has_s_p|	lat	|	lon		|met_pr|  r_dep|time|               r_user|s_pr|s_p_pr|user_id
            "INSERT INTO GAS_STATION VALUES (1, 'bah', 1.375, 1.753, 'via Olanda, 12, Torino', 'Esso',  TRUE, TRUE, FALSE,  TRUE,  FALSE, 45.048903, 7.659812, 0,  		0, '2020-05-24 19:54:07', -1,  1.864, 0,    NULL)",
            "INSERT INTO GAS_STATION VALUES (2, 'Enjoy', 1.431, 1.658, 'via Spagna, 32, Torino', 'Eni', TRUE, TRUE, FALSE,  TRUE,  FALSE, 45.048903, 7.659812, 0, 		0,  '2020-05-23 15:32:09', -1, 1.854, 0,    NULL)"

    );
    static String apiPrefix = "/gasstation";
    private GasStation GS1_existing, GS2_nonExisting;
    private GasStationDto GS1dto;
    Integer GS1_id, GS2_id;


    @Before
    public void setUp() throws SQLException{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //Gas station with existing id in the database
        GS1_existing = new GasStation("Esso12","via Olanda, 12, Torino",true,false,true,true,false,"Enjoy",45.048903, 7.659812,1.375,1.864,0,1.753,0,-1,"2020-05-24 19:54:07",0);
        GS1_id = 1;
        GS1_existing.setGasStationId(GS1_id);
        GS1dto = GasStationConverter.convertEntityToDto(GS1_existing);

        GS2_nonExisting = new GasStation("Esso","via Olanda, 12, Torino",true,false,true,true,false,"bah",45.048903, 7.659812,1.375,1.864,0,1.753,0,-1,"2020-05-24 19:54:07",0);
        GS2_id = 10;
        GS2_nonExisting.setGasStationId(GS2_id);
        GS1dto = GasStationConverter.convertEntityToDto(GS2_nonExisting);



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

    private String convertDtoToJSON(GasStationDto gasStationDto) {
        String JSON =  "{" +
                "\"gasStationName\":\"" + gasStationDto.getGasStationName() + "\"," +
                "\"gasStationAddress\":\"" + gasStationDto.getGasStationAddress() + "\"," +
                "\"hasDiesel\":\"" + gasStationDto.getHasDiesel() + "\"," +
                "\"hasSuper\":" + gasStationDto.getHasSuper() + ","+
                "\"hasSuperPlus\":" + gasStationDto.getHasSuperPlus() + ","+
                "\"hasGas\":" + gasStationDto.getHasGas() + ","+
                "\"hasMethane\":" + gasStationDto.getHasMethane() + ","+
                "\"carSharing\":" + gasStationDto.getCarSharing() + ","+
                "\"lat\":" + gasStationDto.getLat() + ","+
                "\"lon\":" + gasStationDto.getLon() + ","+
                "\"dieselPrice\":" + gasStationDto.getDieselPrice() + ","+
                "\"superPrice\":" + gasStationDto.getSuperPrice() + ","+
                "\"superPlusPrice\":" + gasStationDto.getSuperPlusPrice()+ ","+
                "\"gasPrice\":" + gasStationDto.getGasPrice() + ","+
                "\"methanePrice\":" + gasStationDto.getMethanePrice() + ","+
                "\"reportUser\":" + gasStationDto.getReportUser() + ","+
                "\"userDto\":" + gasStationDto.getUserDto() + ","+
                "\"reportTimestamp\":" + gasStationDto.getReportTimestamp() + ","+
                "\"reportDependability\":" + gasStationDto.getReportDependability() + ","+
                "\"priceReportDtos\":" + gasStationDto.getPriceReportDtos() + ",";

        if (gasStationDto.getGasStationId() != null) {
            JSON += "\"gasStationId\":" + gasStationDto.getGasStationId() + ",";
        }

        JSON += "}";
        System.out.println(JSON);
        return JSON;
    }


    @Test
    public void test_getGasStationById()throws Exception {
        //existing gas station
        mockMvc.perform(get(apiPrefix + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(GS1_id)))
        .accept(MediaType.APPLICATION_JSON)
        .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":false,\"hasSuperPlus\":true,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"2020-05-24 19:54:07\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
        .andExpect(status().isOk())
        .andDo(print());


        //not existing gas station
        mockMvc.perform(get(apiPrefix + GET_GASSTATION_BY_ID.replace("{gasStationId}", String.valueOf(GS2_id)))
                .accept(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void test_getAllGasStations() throws Exception{
        mockMvc.perform(get(apiPrefix + GET_ALL_GASSTATIONS.replace("{gasStationId}", String.valueOf(GS1_id)))
                .accept(MediaType.APPLICATION_JSON)
                .content("[{\"gasStationId\":1,\"gasStationName\":\"Esso\",\"gasStationAddress\":\"via Olanda, 12, Torino\",\"hasDiesel\":true,\"hasSuper\":false,\"hasSuperPlus\":true,\"hasGas\":true,\"hasMethane\":false,\"carSharing\":\"Enjoy\",\"lat\":45.048903,\"lon\":7.659812,\"dieselPrice\":1.375,\"superPrice\":1.846,\"superPlusPrice\":0.0,\"gasPrice\":1.753,\"methanePrice\":0.0,\"reportUser\":-1,\"userDto\":null,\"reportTimestamp\":\"2020-05-24 19:54:07\",\"reportDependability\":0,\"priceReportDtos\":[]}]"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_deleteGasStation() throws Exception {

        //deleting an existing gasStation
        mockMvc.perform(delete(apiPrefix + DELETE_GASSTATION.replace("{gasStationId}", String.valueOf(GS1_id)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$ ").value(true))
                .andDo(print());


        //deleting a non existing gasStation
        mockMvc.perform(get(apiPrefix + DELETE_GASSTATION.replace("{gasStationId}", String.valueOf(GS2_id)))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$ ").value(false))
                .andDo(print());



    }

    /*
    @Test
    public void test_getGasStationsByGasolineType(){

    }

    @Test
    public void test_getGasStationsByProximity(){

    }

    @Test
    public void test_getGasStationsWithCoordinates(){

    }

    @Test
    public void test_setGasStationReport(){

    }


 */





}



