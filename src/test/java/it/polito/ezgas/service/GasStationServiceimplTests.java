package it.polito.ezgas.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;

import exception.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
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
    static String sqlSelectGSbyCarSharing = "SELECT * FROM GAS_STATION WHERE CAR_SHARING='Car2Go';";
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

	static int radius = 5;  // kilometers



	static List<String> sqlInsertGSs = Arrays.asList(
											//id|car|dies_pr|gas_pr|gas_station_address|station_name|has_die|has_g|has_met|has_s|has_s_p|has_p_d|	lat	|	lon		|met_pr|r_dep|time|r_user|s_pr|s_p_pr|p_d_pr|user_id
			"INSERT INTO GAS_STATION VALUES (1, 'Car2Go', 1.375, 1.753, 'via Olanda, 12, Torino', 'Esso',  TRUE, TRUE, FALSE, TRUE, TRUE, FALSE, 45.048903, 7.659812, 0,  		0, NULL, -1, 1.864, 0, 1.555, NULL)",
			"INSERT INTO GAS_STATION VALUES (2, 'Enjoy', 1.431, 1.658, 'via Spagna, 32, Torino', 'Eni', TRUE, TRUE, FALSE, FALSE, TRUE, FALSE, 45.048903, 7.659812, 0, 		0,  NULL, -1, 1.854, 0, 0, NULL)"
    );
	
	private GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
	
	@Autowired
	private GasStationService gasStationService;
	
	Integer GS1id;
	private String GS1Name, GS1carSharing;
	private GasStation GS1;
	private GasStationDto GS1Dto, GS3Dto;
	
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
	    /*    ResultSet rs = st.executeQuery(sqlSelectAllGSs);
	        while (rs.next()) {
	            System.err.println(	"GASSTATIONID: " 		+ rs.getInt("gas_station_id") + " " +
	            					"CARSHARING: " 			+ rs.getString("car_sharing") + " " +
	            					"DIESELPRICE: "			+ rs.getDouble("diesel_price") + " " +
	            					"GASPRICE: " 			+ rs.getDouble("gas_price") + " " +
							        "GASSTATIONADDRESS: " 	+ rs.getString("gas_station_address") + " " +
							        "GASSTATIONNAME: " 		+ rs.getString("gas_station_name") + " " +
							        "HASDIESEL: " 			+ rs.getBoolean("has_diesel") + " " +
							        "HASGAS: " 				+ rs.getBoolean("has_gas") + " " +
							        "HASMETHANE: " 			+ rs.getBoolean("has_methane") + " " +
							        "HASSUPER: " 			+ rs.getBoolean("has_super") + " " +
							        "HASSUPERPLUS: " 		+ rs.getBoolean("has_super_plus") + " " +
							        "LAT: " 				+ rs.getDouble("lat") + " " +
							        "LON: "					+ rs.getDouble("lon") + " " +
							        "METHANEPRICE: "		+ rs.getDouble("methane_price") + " " +
							        "REPORTDEPENDABILITY:"	+ rs.getDouble("report_dependability") + " " +
							        "REPORTTIMESTAMP: "		+ rs.getString("report_timestamp") + " " +
							        "REPORTUSER: "			+ rs.getInt("report_user") + " " +
							        "SUPERPLUSPRICE: "		+ rs.getDouble("super_plus_price") + " " +
							        "SUPERPRICE: "			+ rs.getDouble("super_price") + " " +
							        "USERID: "				+ rs.getInt("user_id")
							        );  
	        }
*/
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
		GS1Name = "Esso";
		GS1carSharing = "Car2Go";
		GS1.setDieselPrice(1.375);
		GS1.setGasPrice(1.753);
		GS1.setGasStationAddress("via Olanda, 12, Torino");
		GS1.setGasStationName(GS1Name);
		GS1.setHasDiesel(true);
		GS1.setHasGas(true);
		GS1.setHasMethane(false);
		GS1.setHasSuper(true);
		GS1.setHasSuperPlus(false);
		GS1.setHasPremiumDiesel(true);
		GS1.setMethanePrice(null);
		GS1.setReportDependability(0);
		GS1.setReportTimestamp(null);
		GS1.setReportUser(-1);
		GS1.setSuperPrice(1.864);
		GS1.setSuperPlusPrice(null);
		GS1.setPremiumDieselPrice(1.555);
		GS1.setGasStationId(GS1id);
		GS1.setLat(45.048903);
		GS1.setLon(7.659812);
		GS1.setReportTimestamp("05-24-2020"); //"05-24-2020"
		GS1.setCarSharing(GS1carSharing);
		//GS1Dto = GasStationConverter.convertEntityToDto(GS1);
		gasStationRepository.save(GS1);

		GS1Dto = new GasStationDto(1, "Esso", "via Olanda, 12, Torino", true, true, false, true, false, true, "Car2Go", 45.048903, 7.659812, 1.375, 1.864, 0.0, 1.753, null, 1.555, -1, "05-24-2020", 0);
		GS3Dto = new GasStationDto(3, "Repsol", "via Portogallo, 43, Torino", true, true, false, true, false, false, "IShare", 45.0, 7.0, 1.375, 1.864, 0.0, 1.753, null, null, -1, "05-25-2020", 0);
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
	public void test_getAllGasStations() throws SQLException {
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
													rs.getBoolean("has_premium_diesel"),
													rs.getString("car_sharing"), 
													rs.getDouble("lat"), 
													rs.getDouble("lon"),                 
													rs.getDouble("diesel_price"),
													rs.getDouble("super_price"),
													rs.getDouble("super_plus_price"),
													rs.getDouble("gas_price"),
													rs.getDouble("methane_price"),
													rs.getDouble("premium_diesel_price"),
													rs.getInt("report_user"),
													rs.getString("report_timestamp"),
													rs.getDouble("report_dependability")            
									);
			gsDtoListDB.add(gsDto);
		}
		
		List<GasStationDto> gsDtoListRepository = gasStationService.getAllGasStations();
		
		assertEquals(gsDtoListDB.size(), gsDtoListRepository.size());
		System.err.println("| gsDtoListDB.size() = " + gsDtoListDB.size() + " | gsDtoListRepository.size() = " + gsDtoListRepository.size() + "|");
		 
        // check if all GSs with same ID are equal
        for (GasStationDto gsDtoDB : gsDtoListDB) {
            for (GasStationDto gsDtoRep : gsDtoListRepository) {
                if (gsDtoDB.getGasStationId().equals(gsDtoRep.getGasStationId())) {
                		/*
        	            System.err.println(	"GASSTATIONID: " 		+ gsDtoDB.getGasStationId() + " " +
        	            					"CARSHARING: " 			+ gsDtoDB.getCarSharing() + " " +
        	            					"DIESELPRICE: "			+ gsDtoDB.getDieselPrice() + " " +
        	            					"GASPRICE: " 			+ gsDtoDB.getGasPrice() + " " +
        							        "GASSTATIONADDRESS: " 	+ gsDtoDB.getGasStationAddress() + " " +
        							        "GASSTATIONNAME: " 		+ gsDtoDB.getGasStationName() + " " +
        							        "HASDIESEL: " 			+ gsDtoDB.getHasDiesel() + " " +
        							        "HASGAS: " 				+ gsDtoDB.getHasGas() + " " +
        							        "HASMETHANE: " 			+ gsDtoDB.getHasMethane() + " " +
        							        "HASSUPER: " 			+ gsDtoDB.getHasSuper() + " " +
        							        "HASSUPERPLUS: " 		+ gsDtoDB.getHasSuperPlus() + " " +
        							        "HASPREMIUMDIESEL: "	+ gsDtoDB.getHasPremiumDiesel() + " " +
        							        "LAT: " 				+ gsDtoDB.getLat() + " " +
        							        "LON: "					+ gsDtoDB.getLon() + " " +
        							        "METHANEPRICE: "		+ gsDtoDB.getMethanePrice() + " " +
        							        "REPORTDEPENDABILITY:"	+ gsDtoDB.getReportDependability() + " " +
        							        "REPORTTIMESTAMP: "		+ gsDtoDB.getReportTimestamp() + " " +
        							        "REPORTUSER: "			+ gsDtoDB.getReportUser() + " " +
        							        "SUPERPLUSPRICE: "		+ gsDtoDB.getSuperPlusPrice() + " " +
        							        "SUPERPRICE: "			+ gsDtoDB.getSuperPrice() + " " +
        							        "PREMIUMDIESELPRICE: "	+ gsDtoDB.getPremiumDieselPrice()
        							        );  
        	            System.err.println(	"GASSTATIONID: " 		+ gsDtoRep.getGasStationId() + " " +
			            					"CARSHARING: " 			+ gsDtoRep.getCarSharing() + " " +
			            					"DIESELPRICE: "			+ gsDtoRep.getDieselPrice() + " " +
			            					"GASPRICE: " 			+ gsDtoRep.getGasPrice() + " " +
									        "GASSTATIONADDRESS: " 	+ gsDtoRep.getGasStationAddress() + " " +
									        "GASSTATIONNAME: " 		+ gsDtoRep.getGasStationName() + " " +
									        "HASDIESEL: " 			+ gsDtoRep.getHasDiesel() + " " +
									        "HASGAS: " 				+ gsDtoRep.getHasGas() + " " +
									        "HASMETHANE: " 			+ gsDtoRep.getHasMethane() + " " +
									        "HASSUPER: " 			+ gsDtoRep.getHasSuper() + " " +
									        "HASSUPERPLUS: " 		+ gsDtoRep.getHasSuperPlus() + " " +
									        "HASPREMIUMDIESEL: " 	+ gsDtoRep.getHasPremiumDiesel() + " " +
									        "LAT: " 				+ gsDtoRep.getLat() + " " +
									        "LON: "					+ gsDtoRep.getLon() + " " +
									        "METHANEPRICE: "		+ gsDtoRep.getMethanePrice() + " " +
									        "REPORTDEPENDABILITY:"	+ gsDtoRep.getReportDependability() + " " +
									        "REPORTTIMESTAMP: "		+ gsDtoRep.getReportTimestamp() + " " +
									        "REPORTUSER: "			+ gsDtoRep.getReportUser() + " " +
									        "SUPERPLUSPRICE: "		+ gsDtoRep.getSuperPlusPrice() + " " +
									        "SUPERPRICE: "			+ gsDtoRep.getSuperPrice() + " " +
									        "PREMIUMDIESELPRICE: "	+ gsDtoRep.getPremiumDieselPrice()
									        );  
        	        */
                	assertTrue(gsDtoDB.equals(gsDtoRep));
                    break;
                }
            }
        }
	}
	
    @Test
    public void test_deleteGasStation_existing() {
        //id exists -> deleted
    	try {
    		assertTrue(gasStationService.deleteGasStation(GS1.getGasStationId()));
        } catch (InvalidGasStationException e) {
            fail("Existing GasStation Id was passed, unexpected InvalidGasStationException");
        }
    }
    
    @Test
    public void test_deleteGasStation_notExisting() {

        //id does not exist -> throw exception
        try {
            gasStationService.deleteGasStation(1000);
            fail("Expected InvalidGasStationException");
        } catch (InvalidGasStationException e) {
            assertEquals(e.getMessage(), "GasStation not found");
        }
    }
    
    @Test
    public void test_getGasStationsByGasolineType_InvalidGasType() {
    	 //GasolineType does not exist -> throw exception
        try {
            gasStationService.getGasStationsByGasolineType("NotAValidGasType");
            fail("Expected InvalidGastTypeException");
        } catch (InvalidGasTypeException e) {
            assertEquals(e.getMessage(), "Gasoline Type not found");
        }
    }
    
    @Test
    public void test_getGasStationsByGasolineType_validGasType() throws SQLException {
    	List<GasStationDto> gsDtoListDB = new ArrayList<>();
		
    	ResultSet rs = st.executeQuery(sqlSelectGSbyGasType);
		while(rs.next()) {
			GasStationDto gsDto = new GasStationDto(rs.getInt("gas_station_id"),
													rs.getString("gas_station_name"),
													rs.getString("gas_station_address"),
													rs.getBoolean("has_diesel"),
													rs.getBoolean("has_super"),
													rs.getBoolean("has_super_plus"),
													rs.getBoolean("has_gas"), 
													rs.getBoolean("has_methane"),
													rs.getBoolean("has_premium_diesel"),
													rs.getString("car_sharing"), 
													rs.getDouble("lat"), 
													rs.getDouble("lon"),                 
													rs.getDouble("diesel_price"),
													rs.getDouble("super_price"),
													rs.getDouble("super_plus_price"),
													rs.getDouble("gas_price"),
													rs.getDouble("methane_price"),
													rs.getDouble("premium_diesel_price"),
													rs.getInt("report_user"),
													rs.getString("report_timestamp"),
													rs.getDouble("report_dependability")            
									);
			gsDtoListDB.add(gsDto);
		}

		List<GasStationDto> gsDtoListRepository = new ArrayList<>();
		
    	//GasolineType exist -> check if size of returned list is equal
        try {
        	gsDtoListRepository = gasStationService.getGasStationsByGasolineType("Diesel");
        } catch (InvalidGasTypeException e) {
            fail("Unexpected InvalidGasStation exception");
        }
        
        assertEquals(gsDtoListDB.size(), gsDtoListRepository.size());
        //assertTrue(GS1Dto.equals(gsDtoListRepository.get(0)));
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
		int userTrustLevel = 2;
		double expectedRes = 35;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability("12-11-2019", "12-19-2019", userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_notObsolescent() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();

		int userTrustLevel = 2;
		double expectedRes = 77.857;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability("12-11-2019", "12-12-2019", userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_perfectUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		int userTrustLevel = 5;
		double expectedRes = 100;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability("12-12-2019", "12-12-2019", userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		int userTrustLevel = -5;
		double expectedRes = 50;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability("12-12-2019", "12-12-2019", userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		int userTrustLevel = -5;
		double expectedRes = 0;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability("12-11-2019", "12-19-2019", userTrustLevel), 0.01);
	}
	
	@Test
	public void test_getGasStationsByProximity_invalidGPS() {
		try {
			gasStationService.getGasStationsByProximity(91, 45);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(-91, 45);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(45, 181);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsByProximity(45, -181);
			fail("Expected GPSDataException");
		} catch (GPSDataException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
	}
	
	@Test
	public void test_getGasStationsWithCoordinates_invalidGPS() throws InvalidGasTypeException {
		try {
			gasStationService.getGasStationsWithCoordinates(91, 45, radius, "Diesel", "Car2Go");
			fail("Expected GPSDataException");
		} catch (GPSDataException | InvalidCarSharingException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsWithCoordinates(-91, 45, radius, "Diesel", "Car2Go");
			fail("Expected GPSDataException");
		} catch (GPSDataException | InvalidCarSharingException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsWithCoordinates(45, 181, radius, "Diesel", "Car2Go");
			fail("Expected GPSDataException");
		} catch (GPSDataException | InvalidCarSharingException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
		
		try {
			gasStationService.getGasStationsWithCoordinates(45, -181, radius, "Diesel", "Car2Go");
			fail("Expected GPSDataException");
		} catch (GPSDataException | InvalidCarSharingException e) {
			assertEquals(e.getMessage(), "Invalid GPS Data");
		}
	}
	
	@Test
	public void test_getGasStationsWithCoordinates_invalidGasType() throws GPSDataException {
		 //GasolineType does not exist -> throw exception
        try {
            gasStationService.getGasStationsWithCoordinates(45, 45, radius, "NotAValidGasType", "Car2Go");
            fail("Expected InvalidGastTypeException");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            assertEquals(e.getMessage(), "Invalid Gasoline Type");
        }
	}
	
	@Test
	public void test_getGasStationsWithCoordinates_existing() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		List<GasStationDto> gsDtos = gasStationService.getGasStationsWithCoordinates(45.048903, 7.659812, radius, "Diesel", "Car2Go");
				
		assertEquals(1, gsDtos.size());
		assertTrue(GS1Dto.equals(gsDtos.get(0)));
	}
	
	@Test
	public void test_getGasStationsWithCoordinates_notExisting() throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException {
		try {
			gasStationService.getGasStationsWithCoordinates(45.048903, 7.659812, radius, "Diesel", "NonAnExistingCarSharing");
			fail("Expected InvalidCarSharingException");
		} catch (InvalidCarSharingException e){
			assertEquals(e.getMessage(), "Invalid CarSharing Type");
		}
	}	
	
	@Test
	public void test_getGasStationsWithoutCoordinates_invalidGasType() {
		 //GasolineType does not exist -> throw exception
        try {
            gasStationService.getGasStationsWithoutCoordinates("NotAValidGasType", "Car2Go");
            fail("Expected InvalidGastTypeException");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            assertEquals(e.getMessage(), "Invalid Gasoline Type");
        }
	}
	
	@Test
	public void test_getGasStationsWithoutCoordinates_existing() throws InvalidGasTypeException, InvalidCarSharingException {
		List<GasStationDto> gsDtos = new ArrayList<>();
		
		gsDtos = gasStationService.getGasStationsWithoutCoordinates("Diesel", "null");
		assertEquals(2, gsDtos.size());
		//assertTrue(GS1Dto.equals(gsDtos.get(0)));
		System.err.println(	"GASSTATIONID: " 		+ GS1Dto.getGasStationId() + " " +
							"CARSHARING: " 			+ GS1Dto.getCarSharing() + " " +
							"DIESELPRICE: "			+ GS1Dto.getDieselPrice() + " " +
							"GASPRICE: " 			+ GS1Dto.getGasPrice() + " " +
					        "GASSTATIONADDRESS: " 	+ GS1Dto.getGasStationAddress() + " " +
					        "GASSTATIONNAME: " 		+ GS1Dto.getGasStationName() + " " +
					        "HASDIESEL: " 			+ GS1Dto.getHasDiesel() + " " +
					        "HASGAS: " 				+ GS1Dto.getHasGas() + " " +
					        "HASMETHANE: " 			+ GS1Dto.getHasMethane() + " " +
					        "HASSUPER: " 			+ GS1Dto.getHasSuper() + " " +
					        "HASSUPERPLUS: " 		+ GS1Dto.getHasSuperPlus() + " " +
					        "HASPREMIUMDIESEL: "	+ GS1Dto.getHasPremiumDiesel() + " " +
					        "LAT: " 				+ GS1Dto.getLat() + " " +
					        "LON: "					+ GS1Dto.getLon() + " " +
					        "METHANEPRICE: "		+ GS1Dto.getMethanePrice() + " " +
					        "REPORTDEPENDABILITY:"	+ GS1Dto.getReportDependability() + " " +
					        "REPORTTIMESTAMP: "		+ GS1Dto.getReportTimestamp() + " " +
					        "REPORTUSER: "			+ GS1Dto.getReportUser() + " " +
					        "SUPERPLUSPRICE: "		+ GS1Dto.getSuperPlusPrice() + " " +
					        "SUPERPRICE: "			+ GS1Dto.getSuperPrice() + " " +
					        "PREMIUMDIESELPRICE: "	+ GS1Dto.getPremiumDieselPrice()
					        );  
		System.err.println(	"GASSTATIONID: " 		+ gsDtos.get(0).getGasStationId() + " " +
							"CARSHARING: " 			+ gsDtos.get(0).getCarSharing() + " " +
							"DIESELPRICE: "			+ gsDtos.get(0).getDieselPrice() + " " +
							"GASPRICE: " 			+ gsDtos.get(0).getGasPrice() + " " +
					        "GASSTATIONADDRESS: " 	+ gsDtos.get(0).getGasStationAddress() + " " +
					        "GASSTATIONNAME: " 		+ gsDtos.get(0).getGasStationName() + " " +
					        "HASDIESEL: " 			+ gsDtos.get(0).getHasDiesel() + " " +
					        "HASGAS: " 				+ gsDtos.get(0).getHasGas() + " " +
					        "HASMETHANE: " 			+ gsDtos.get(0).getHasMethane() + " " +
					        "HASSUPER: " 			+ gsDtos.get(0).getHasSuper() + " " +
					        "HASSUPERPLUS: " 		+ gsDtos.get(0).getHasSuperPlus() + " " +
					        "HASPREMIUMDIESEL: " 	+ gsDtos.get(0).getHasPremiumDiesel() + " " +
					        "LAT: " 				+ gsDtos.get(0).getLat() + " " +
					        "LON: "					+ gsDtos.get(0).getLon() + " " +
					        "METHANEPRICE: "		+ gsDtos.get(0).getMethanePrice() + " " +
					        "REPORTDEPENDABILITY:"	+ gsDtos.get(0).getReportDependability() + " " +
					        "REPORTTIMESTAMP: "		+ gsDtos.get(0).getReportTimestamp() + " " +
					        "REPORTUSER: "			+ gsDtos.get(0).getReportUser() + " " +
					        "SUPERPLUSPRICE: "		+ gsDtos.get(0).getSuperPlusPrice() + " " +
					        "SUPERPRICE: "			+ gsDtos.get(0).getSuperPrice() + " " +
					        "PREMIUMDIESELPRICE: "	+ gsDtos.get(0).getPremiumDieselPrice()
				        );
		GasStationDto other = gsDtos.get(0);
		assertTrue(GS1Dto.equals(other));

		
		gsDtos = gasStationService.getGasStationsWithoutCoordinates("LPG", "Car2Go");
		assertEquals(1, gsDtos.size());
		assertTrue(GS1Dto.equals(gsDtos.get(0)));
		
		gsDtos = gasStationService.getGasStationsWithoutCoordinates("Super", "Enjoy");
		assertEquals(0, gsDtos.size());
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
			assertEquals("Invalid GPS Data", e.getMessage());
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
	public void test_saveGasStation_negativePrices() throws GPSDataException {
		GS1Dto.setDieselPrice(-1.2);
		try {
			gasStationService.saveGasStation(GS1Dto);
			fail("Expected PriceException for negative prices");
		}catch(PriceException e) {
			//assertEquals(e.getMessage(), "Wrong Exception");
		}
	}
	
	@Test
	public void test_saveGasStation_existing() throws PriceException, GPSDataException, InvalidGasStationException {
		GS1Dto.setDieselPrice(1.524);
		GS1Dto.setHasMethane(true);
		GS1Dto.setMethanePrice(0.986);
		
		GasStationDto gsDto = gasStationService.saveGasStation(GS1Dto);
		
		assertTrue(gsDto.getGasStationId() == GS1Dto.getGasStationId());
		assertTrue(gasStationService.getGasStationById(GS1Dto.getGasStationId()).getDieselPrice() == 1.524 );
		assertTrue(gasStationService.getGasStationById(GS1Dto.getGasStationId()).getHasMethane());
		assertTrue(gasStationService.getGasStationById(GS1Dto.getGasStationId()).getMethanePrice() == 0.986);
	}
	
	@Test
	public void test_saveGasStation_notExisting() throws PriceException, GPSDataException, InvalidGasStationException {
		GasStationDto gsDto = gasStationService.saveGasStation(GS3Dto);
		//assertTrue(gsDto.equals(GS3Dto));
		System.err.println(	"GASSTATIONID: " 		+ GS3Dto.getGasStationId() + " " +
							"CARSHARING: " 			+ GS3Dto.getCarSharing() + " " +
							"DIESELPRICE: "			+ GS3Dto.getDieselPrice() + " " +
							"GASPRICE: " 			+ GS3Dto.getGasPrice() + " " +
					        "GASSTATIONADDRESS: " 	+ GS3Dto.getGasStationAddress() + " " +
					        "GASSTATIONNAME: " 		+ GS3Dto.getGasStationName() + " " +
					        "HASDIESEL: " 			+ GS3Dto.getHasDiesel() + " " +
					        "HASGAS: " 				+ GS3Dto.getHasGas() + " " +
					        "HASMETHANE: " 			+ GS3Dto.getHasMethane() + " " +
					        "HASSUPER: " 			+ GS3Dto.getHasSuper() + " " +
					        "HASSUPERPLUS: " 		+ GS3Dto.getHasSuperPlus() + " " +
					        "HASPREMIUMDIESEL: "	+ GS3Dto.getHasPremiumDiesel() + " " +
					        "LAT: " 				+ GS3Dto.getLat() + " " +
					        "LON: "					+ GS3Dto.getLon() + " " +
					        "METHANEPRICE: "		+ GS3Dto.getMethanePrice() + " " +
					        "REPORTDEPENDABILITY:"	+ GS3Dto.getReportDependability() + " " +
					        "REPORTTIMESTAMP: "		+ GS3Dto.getReportTimestamp() + " " +
					        "REPORTUSER: "			+ GS3Dto.getReportUser() + " " +
					        "SUPERPLUSPRICE: "		+ GS3Dto.getSuperPlusPrice() + " " +
					        "SUPERPRICE: "			+ GS3Dto.getSuperPrice() + " " +
					        "PREMIUMDIESELPRICE: "	+ GS3Dto.getPremiumDieselPrice()
				);  
	System.err.println(	"GASSTATIONID: " 		+ gsDto.getGasStationId() + " " +
						"CARSHARING: " 			+ gsDto.getCarSharing() + " " +
						"DIESELPRICE: "			+ gsDto.getDieselPrice() + " " +
						"GASPRICE: " 			+ gsDto.getGasPrice() + " " +
				        "GASSTATIONADDRESS: " 	+ gsDto.getGasStationAddress() + " " +
				        "GASSTATIONNAME: " 		+ gsDto.getGasStationName() + " " +
				        "HASDIESEL: " 			+ gsDto.getHasDiesel() + " " +
				        "HASGAS: " 				+ gsDto.getHasGas() + " " +
				        "HASMETHANE: " 			+ gsDto.getHasMethane() + " " +
				        "HASSUPER: " 			+ gsDto.getHasSuper() + " " +
				        "HASSUPERPLUS: " 		+ gsDto.getHasSuperPlus() + " " +
				        "HASPREMIUMDIESEL: " 	+ gsDto.getHasPremiumDiesel() + " " +
				        "LAT: " 				+ gsDto.getLat() + " " +
				        "LON: "					+ gsDto.getLon() + " " +
				        "METHANEPRICE: "		+ gsDto.getMethanePrice() + " " +
				        "REPORTDEPENDABILITY:"	+ gsDto.getReportDependability() + " " +
				        "REPORTTIMESTAMP: "		+ gsDto.getReportTimestamp() + " " +
				        "REPORTUSER: "			+ gsDto.getReportUser() + " " +
				        "SUPERPLUSPRICE: "		+ gsDto.getSuperPlusPrice() + " " +
				        "SUPERPRICE: "			+ gsDto.getSuperPrice() + " " +
				        "PREMIUMDIESELPRICE: "	+ gsDto.getPremiumDieselPrice()
		        );  
		assertTrue(gsDto.equals(GS3Dto));  
		assertTrue(gasStationService.getGasStationById(GS3Dto.getGasStationId()).equals(gsDto));
	}
	
	
	@Test
	public void test_setReport_invalidPrice() throws InvalidGasStationException, InvalidUserException {
		try {
			gasStationService.setReport(GS1.getGasStationId(), GS1.getDieselPrice(), -1.0, GS1.getSuperPlusPrice(), GS1.getGasPrice(), GS1.getMethanePrice(), GS1.getPremiumDieselPrice(), 1);
			fail("Expected PriceException");
		}catch(PriceException e){
			assertEquals("Wrong Price", e.getMessage());
		}
	}
	
	@Test
	public void test_setReport_invalidGasStation() throws InvalidUserException, PriceException {
		try {
			gasStationService.setReport(1000, GS1.getDieselPrice(), GS1.getSuperPrice(), GS1.getSuperPlusPrice(), GS1.getGasPrice(), GS1.getMethanePrice(), GS1.getPremiumDieselPrice(), 1);
			fail("Expected InvalidGasStationException");
		}catch(InvalidGasStationException e){
			assertEquals("Gas Station not found", e.getMessage());
		}
	}
	
	@Test
	public void test_setReport_invalidUser() throws PriceException, InvalidGasStationException {
		try {
			gasStationService.setReport(GS1.getGasStationId(), GS1.getDieselPrice(), GS1.getSuperPrice(), GS1.getSuperPlusPrice(), GS1.getGasPrice(), GS1.getMethanePrice(), GS1.getPremiumDieselPrice(), 1000);
			fail("Expected InvalidUserException");
		}catch(InvalidUserException e){
			assertEquals("User not found", e.getMessage());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_setReport() throws InvalidGasStationException, PriceException, InvalidUserException {
		Double oldDieselPrice = GS1.getDieselPrice();
		Double oldSuperPrice = GS1.getSuperPrice();
		Double oldGasPrice = GS1.getGasPrice();
		
		gasStationService.setReport(GS1.getGasStationId(), ++oldDieselPrice, ++oldSuperPrice, GS1.getSuperPlusPrice(), ++oldGasPrice, GS1.getMethanePrice(), GS1.getPremiumDieselPrice(), 1);
		assertEquals(oldDieselPrice, gasStationService.getGasStationById(GS1.getGasStationId()).getDieselPrice(), 0);
		assertEquals(oldSuperPrice, gasStationService.getGasStationById(GS1.getGasStationId()).getSuperPrice(), 0);
		assertEquals(oldGasPrice, gasStationService.getGasStationById(GS1.getGasStationId()).getGasPrice(), 0);
	}
	
	@Test
	public void test_getGasStationByCarSharing_existing() throws SQLException {
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
													rs.getBoolean("has_premium_diesel"),
													rs.getString("car_sharing"), 
													rs.getDouble("lat"), 
													rs.getDouble("lon"),                 
													rs.getDouble("diesel_price"),
													rs.getDouble("super_price"),
													rs.getDouble("super_plus_price"),
													rs.getDouble("gas_price"),
													rs.getDouble("methane_price"),
													rs.getDouble("premium_diesel_price"),
													rs.getInt("report_user"),
													rs.getString("report_timestamp"),
													rs.getDouble("report_dependability")            
									);
			gsDtoListDB.add(gsDto);
		}
		
		List<GasStationDto> gsDtoListRepository = gasStationService.getGasStationByCarSharing(GS1carSharing);
		
		assertEquals(gsDtoListDB.size(), gsDtoListRepository.size());
	}
	
	
	@Test
	public void test_getGasStationByCarSharing_notExisting() throws SQLException {
		List<GasStationDto> gsDtoListRepository = gasStationService.getGasStationByCarSharing("notExistingCarSharing");
		
		assertEquals(0, gsDtoListRepository.size());
	}
}