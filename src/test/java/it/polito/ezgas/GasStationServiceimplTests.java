package it.polito.ezgas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;

import static org.junit.Assert.*;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.impl.GasStationServiceimpl;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.service.GasStationService;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class GasStationServiceimplTests {
	@Autowired
    GasStationRepository gasStationRepository;
	
	static Connection db;
    static Statement st;
    static ResultSet backup;
    static String sqlSelectAllGSs = "SELECT * FROM GAS_STATION";
    static String sqlSelectGSbyCarSharing = "SELECT * FROM GAS_STATION WHERE CAR_SHARING='bah';";
    static String sqlDropGSTable = "DROP TABLE IF EXISTS GAS_STATION";
    static String sqlCreateGSTable = "CREATE TABLE GAS_STATION " +
                                       "(gas_station_id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
                                       "gas_station_name VARCHAR(255), " +
                                       "gas_station_address VARCHAR(255), " +
                                       "has_diesel BOOLEAN, " +
                                       "has_super BOOLEAN, " +
                                       "has_super_Plus BOOLEAN, " +
                                       "has_gas BOOLEAN, " +
                                       "has_methane BOOLEAN, " +
                                       "car_sharing VARCHAR(255), " +
                                       "lat DOUBLE, " +
                                       "lon DOUBLE, " +
                                       "diesel_price DOUBLE, " +
                                       "super_price DOUBLE, " +
                                       "super_plus_price DOUBLE, " +
                                       "gas_price DOUBLE, " +
                                       "methane_price DOUBLE, " +
                                       "report_user INTEGER, " +
                                       "report_timestamp VARCHAR(255), "+
                                       "report_dependability DOUBLE)";
    
    static List<String> sqlInsertGSs = Arrays.asList(
            "INSERT INTO GAS_STATION VALUES (1, 'Esso', 'via Olanda, 12, Torino', TRUE, TRUE, FALSE, TRUE, FALSE, 'bah', 45.048903, 7.659812, 1.375, 1.872,  NULL, 1.756, NULL, NULL, NULL, NULL)",
            "INSERT INTO GAS_STATION VALUES (2, 'Eni', 'via Spagna, 32, Torino', TRUE, TRUE, FALSE, TRUE, FALSE, 'enjoy', 45.048903, 7.659812, 1.375, 1.872,  NULL, 1.756, NULL, NULL, NULL, NULL)"

    );
	
	private GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
	
	@Autowired
	private GasStationService gasStationService;
	
	private Integer GS1id;
	private String GS1carSharing;
	private GasStation GS1;
	private GasStationDto GS1Dto;
	
	@PostConstruct
    @BeforeClass  // run only once
    public static void setUpDatabase() throws SQLException {
        db = DriverManager.getConnection("jdbc:h2:./data/test", "sa", "password");
        st = db.createStatement();
//	        backup = st.executeQuery(sqlSelectAllGSs);
        st.executeUpdate(sqlDropGSTable);
        st.executeUpdate(sqlCreateGSTable);
        for (String sql : sqlInsertGSs) {
            st.executeUpdate(sql);
        }

        // uncomment following lines for debugging
	        ResultSet rs = st.executeQuery(sqlSelectAllGSs);
	        while (rs.next()) {
	            System.err.println(	"GASSTATIONID: " 		+ rs.getInt("gas_station_id") + " " +
							        "GASSTATIONNAME: " 		+ rs.getString("gas_station_name") + " " +
							        "GASSTATIONADDRESS: " 	+ rs.getString("gas_station_address") + " " +
							        "HASDIESEL: " 			+ rs.getBoolean("has_diesel") + " " +
							        "HASSUPER: " 			+ rs.getBoolean("has_super") + " " +
							        "HASSUPERPLUS: " 		+ rs.getBoolean("has_super_plus") + " " +
							        "HASGAS: " 				+ rs.getBoolean("has_gas") + " " +
							        "HASMETHANE: " 			+ rs.getBoolean("has_methane") + " " +
							        "CARSHARING: " 			+ rs.getString("car_sharing") + " " +
							        "LAT: " 				+ rs.getDouble("lat") + " " +
							        "LON: "					+ rs.getDouble("lon") + " " +
							        "DIESELPRICE: "			+ rs.getDouble("diesel_price") + " " +
							        "SUPERPRICE: "			+ rs.getDouble("super_price") + " " +
							        "SUPERPLUSPRICE: "		+ rs.getDouble("super_plus_price") + " " +
							        "GASPRICE: " 			+ rs.getDouble("gas_price") + " " +
							        "METHANEPRICE: "		+ rs.getDouble("methane_price") + " " +
							        "REPORTUSER: "			+ rs.getInt("report_user") + " " +
							        "REPORTTIMESTAMP: "		+ rs.getString("report_timestamp") + " " +
							        "REPORTDEPENDABILITY:"	+ rs.getDouble("report_dependability")
                    );
	        }

	        //System.exit(33);
    }

	@AfterClass  // run only once
	public static void tearDown() throws SQLException {
	    st.close();
	    db.close();
	}
	
	@Before
	public void setUp() {
		GS1 = new GasStation();
		GS1id = 1;
		GS1carSharing = "bah";
		GS1.setGasStationId(GS1id);
		GS1.setLat(45.048903);
		GS1.setLon(7.659812);
		GS1.setCarSharing(GS1carSharing);
		GS1Dto = GasStationConverter.convertEntityToDto(GS1);
		
	}

	@Test
	public void test_getGasStationById_existing() throws InvalidGasStationException {
	    assertTrue(GS1Dto.equals(gasStationService.getGasStationById(GS1id)));
	}
	
	@Test
	public void test_getGasStationById_notExisting() {
		try {
	        gasStationService.getGasStationById(1000);
	        fail("Expected InvalidGasStationException for non existing gasStationId");
	    } catch (InvalidGasStationException e) {
	        assertEquals(e.getMessage(), "GasStation not found");
	    }
	}
	 
	@Test
	public void test_GetAllGasStations() throws SQLException {
		List<GasStationDto> gsDtoListDB = new ArrayList<>();
		ResultSet rs = st.executeQuery(sqlSelectAllGSs);
		while(rs.next()) {
			GasStationDto gsDto = new GasStationDto(rs.getInt("gas_station_id"),
													rs.getString("gas_station_name"),
													rs.getString("gas_station_address"),
													rs.getBoolean("has_diesel"),
													rs.getBoolean("has_super"),
													rs.getBoolean("has_super_plus"),
													rs.getBoolean("has_gas"), 
													rs.getBoolean("has_methane"),   
													rs.getString("car_sharing"), 
													rs.getDouble("lat"), 
													rs.getDouble("lon"),                 
													rs.getDouble("diesel_price"),
													rs.getDouble("super_price"),
													rs.getDouble("super_plus_price"),
													rs.getDouble("gas_price"),
													rs.getDouble("methane_price"),
													rs.getInt("report_user"),
													rs.getString("report_timestamp"),
													rs.getDouble("report_dependability")            
									);
			gsDtoListDB.add(gsDto);
		}
		
		List<GasStationDto> gsDtoListRepository = gasStationService.getAllGasStations();
		
		assertEquals(gsDtoListDB.size(), gsDtoListRepository.size());

        // check if all GSs with same ID are equal
        for (GasStationDto gsDB : gsDtoListDB) {
            for (GasStationDto gasStationRepository : gsDtoListRepository) {
                if (gsDB.getGasStationId().equals(gasStationRepository.getGasStationId())) {
                    assertTrue(gsDB.equals(gasStationRepository));
                    break;
                }
            }
        }
	}
	
    @Test
    public void test_DeleteGasStation_existing() throws InvalidGasStationException {
        //id exists -> deleted
        assertTrue(gasStationService.deleteGasStation(GS1.getGasStationId()));
    }
    
    @Test
    public void test_DeleteGasStation_notExisting() {

        //id does not exist -> throw exception
        try {
            gasStationService.deleteGasStation(1000);
            fail("Expected InvalidGasStationException");
        } catch (InvalidGasStationException e) {
            assertEquals(e.getMessage(), "GasStation not found");
        }
    }
    
	@Test 
	public void test_distance_nearest(){
		//retitalia c.so Duca Abruzzi 45.064666, 7.664134
		//poliTo 45.062453, 7.662429
		//~0.3km 0.2mi
		assertEquals(0.3, gasStationServiceimpl.distance(45.064666, 7.664134, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_near(){
		//repsol c.so lepanto 45.047977, 7.660156
		//poliTo 45.062453, 7.662429
		//~1.6km 1mi
		assertEquals(1.6, gasStationServiceimpl.distance(45.047977, 7.660156, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_far(){
		//punto benzina c.so Casale 45.070408, 7.727038
		//poliTo 45.062453, 7.662429
		//~5.1km 3.1mi
		assertEquals(5.1, gasStationServiceimpl.distance(45.070408, 7.727038, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_furthest(){
		//Gas Station s.r.l. 45.072812, 7.573000
		//poliTo 45.062453, 7.662429
		//~7.1km 4.4mi
		assertEquals(7.1, gasStationServiceimpl.distance(45.072812, 7.573000, 45.062453, 7.662429), 0.2);
	}
	
	//	Dependability = 50 * (userTrustLevel +5)/10 + 50 * obsolescence 
	//	if (newTimeStamp - lastTimeStamp) > 7 days? obsolescence = 0 : obsolescence = 1 - (today - P.time_tag)/7;
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 35;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_notObsolescent() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 20, 40, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 77.857;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_perfectUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 19, 22, 32, 0);
		int userTrustLevel = 5;
		double expectedRes = 100;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 21, 30, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 50;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 0;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@Test
	public void test_getGasStationsByProximity_invalidGPS() {
		try {
			gasStationService.getGasStationsByProximity(91, 45);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Ivalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(-91, 45);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Ivalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(45, 181);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Ivalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(45, -181);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Ivalid GPS Data");
		}
	}
	
	@Test
	public void test_saveGasStation_invalidGPS() throws PriceException {
		GasStationDto gsDto_invalidGps;
		
		GS1.setLat(91);
		GS1.setLon(45);
		gsDto_invalidGps = GasStationConverter.convertEntityToDto(GS1);
		
		try {
			gasStationService.saveGasStation(gsDto_invalidGps);
			fail("Expected GPSDataException for invalid lat value");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		GS1.setLat(-91);
		GS1.setLon(45);
		gsDto_invalidGps = GasStationConverter.convertEntityToDto(GS1);
		
		try {
			gasStationService.saveGasStation(gsDto_invalidGps);
			fail("Expected GPSDataException for invalid lat value");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		GS1.setLat(45);
		GS1.setLon(181);
		gsDto_invalidGps = GasStationConverter.convertEntityToDto(GS1);
		
		try {
			gasStationService.saveGasStation(gsDto_invalidGps);
			fail("Expected GPSDataException for invalid lon value");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		GS1.setLat(45);
		GS1.setLon(-181);
		gsDto_invalidGps = GasStationConverter.convertEntityToDto(GS1);
		
		try {
			gasStationService.saveGasStation(gsDto_invalidGps);
			fail("Expected GPSDataException for invalid lon value");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
	}
	
	@Test
	public void test_getGasStationByCarSharing() throws SQLException {
		List<GasStationDto> gsDtoListDB = new ArrayList<>();
		ResultSet rs = st.executeQuery(sqlSelectGSbyCarSharing);
		while(rs.next()) {
			GasStationDto gsDto = new GasStationDto(rs.getInt("gas_station_id"),
													rs.getString("gas_station_name"),
													rs.getString("gas_station_address"),
													rs.getBoolean("has_diesel"),
													rs.getBoolean("has_super"),
													rs.getBoolean("has_super_plus"),
													rs.getBoolean("has_gas"), 
													rs.getBoolean("has_methane"),   
													rs.getString("car_sharing"), 
													rs.getDouble("lat"), 
													rs.getDouble("lon"),                 
													rs.getDouble("diesel_price"),
													rs.getDouble("super_price"),
													rs.getDouble("super_plus_price"),
													rs.getDouble("gas_price"),
													rs.getDouble("methane_price"),
													rs.getInt("report_user"),
													rs.getString("report_timestamp"),
													rs.getDouble("report_dependability")            
									);
			gsDtoListDB.add(gsDto);
		}
		
		List<GasStationDto> gsDtoListRepository = gasStationService.getGasStationByCarSharing(GS1carSharing);
		
		assertEquals(gsDtoListDB.size(), gsDtoListRepository.size());
	}
	
}