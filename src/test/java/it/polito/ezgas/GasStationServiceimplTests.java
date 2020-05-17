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

import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;

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
    static String sqlSelectAllGSs = "SELECT * FROM GS";
    static String sqlDropGSTable = "DROP TABLE IF EXISTS GS";
    static String sqlCreateGSTable = "CREATE TABLE GS " +
                                       "(gasStationId INTEGER NOT NULL, " +
                                       "gasStationName VARCHAR(255), " +
                                       "gasStationAddress VARCHAR(255), " +
                                       "hasDiesel BOOLEAN, " +
                                       "hasSuper BOOLEAN, " +
                                       "hasSuperPlus BOOLEAN, " +
                                       "hasGas BOOLEAN, " +
                                       "hasMethane BOOLEAN, " +
                                       "carSharing VARCHAR(255), " +
                                       "lat FLOAT, " +
                                       "lon FLOAT, " +
                                       "dieselPrice FLOAT, " +
                                       "superPrice FLOAT, " +
                                       "superPlusPrice FLOAT, " +
                                       "gasPrice FLOAT, " +
                                       "methanePrice FLOAT, " +
                                       "reportUser INTEGER, " +
                                       "reportTimestamp VARCHAR(255), "+
                                       "reportDependability FLOAT, " +
                                       "PRIMARY KEY (gasStationId))";
    
    static List<String> sqlInsertGSs = Arrays.asList(
            "INSERT INTO GS VALUES (1, 'Esso', 'via Olanda, 12, Torino', TRUE, TRUE, FALSE, TRUE, FALSE, 'bah', 45.048903, 7.659812, 1.375, 1.872,  NULL, 1.756, NULL, NULL, NULL, NULL)",
            "INSERT INTO GS VALUES (2, 'Eni', 'via Spagna, 32, Torino', TRUE, TRUE, FALSE, TRUE, FALSE, 'enjoy', 45.048903, 7.659812, 1.375, 1.872,  NULL, 1.756, NULL, NULL, NULL, NULL)"

    );
	
	private GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
	
	@Autowired
	private GasStationService gasStationService;
	private Integer GS1id;
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
	            System.err.println(	"GASSTATIONID: " 		+ rs.getInt("gasStationId") + " " +
							        "GASSTATIONNAME: " 		+ rs.getString("gasStationName") + " " +
							        "GASSTATIONADDRESS: " 	+ rs.getString("gasStationAddress") + " " +
							        "HASDIESEL: " 			+ rs.getBoolean("hasDiesel") + " " +
							        "HASSUPER: " 			+ rs.getBoolean("hasSuper") + " " +
							        "HASSUPERPLUS: " 		+ rs.getBoolean("hasSuperPlus") + " " +
							        "HASGAS: " 				+ rs.getBoolean("hasGas") + " " +
							        "HASMETHANE: " 			+ rs.getBoolean("hasMethane") + " " +
							        "CARSHARING: " 			+ rs.getString("carSharing") + " " +
							        "LAT: " 				+ rs.getFloat("lat") + " " +
							        "LON: "					+ rs.getFloat("lon") + " " +
							        "DIESELPRICE: "			+ rs.getFloat("dieselPrice") + " " +
							        "SUPERPRICE: "			+ rs.getFloat("superPrice") + " " +
							        "SUPERPLUSPRICE: "		+ rs.getFloat("superPlusPrice") + " " +
							        "GASPRICE: " 			+ rs.getFloat("gasPrice") + " " +
							        "METHANEPRICE: "		+ rs.getFloat("methanePrice") + " " +
							        "REPORTUSER: "			+ rs.getInt("reportUser") + " " +
							        "REPORTTIMESTAMP: "		+ rs.getString("reportTimestamp") + " " +
							        "REPORTDEPENDABILITY:"	+ rs.getFloat("reportDependability")
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
		GS1.setGasStationId(GS1id);
		GS1.setLat(45.048903);
		GS1.setLon(7.659812);
		
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
	        fail("Expected InvalidGasStationException");
	    } catch (InvalidGasStationException e) {
	        assertEquals(e.getMessage(), "GasStation not found");
	    }
	}
	 
	@Test
	public void test_GetAllGasStations() throws SQLException {
		List<GasStationDto> gssDB = new ArrayList<>();
		ResultSet rs = st.executeQuery(sqlSelectAllGSs);
		while(rs.next()) {
			GasStationDto gsDto = new GasStationDto(rs.getInt("gasStationId"),
													rs.getString("gasStationName"),
													rs.getString("gasStationAddress"),
													rs.getBoolean("hasDiesel"),
													rs.getBoolean("hasSuper"),
													rs.getBoolean("hasSuperPlus"),
													rs.getBoolean("hasGas"), 
													rs.getBoolean("hasMethane"),   
													rs.getString("carSharing"), 
													rs.getFloat("lat"), 
													rs.getFloat("lon"),                 
													rs.getFloat("dieselPrice"),
													rs.getFloat("superPrice"),
													rs.getFloat("superPlusPrice"),
													rs.getFloat("gasPrice"),
													rs.getFloat("methanePrice"),
													rs.getInt("reportUser"),
													rs.getString("reportTimestamp"),
													rs.getFloat("reportDependability")            
									);
			gssDB.add(gsDto);
		}
		
		List<GasStationDto> gssRepository = gasStationService.getAllGasStations();
		
		assertEquals(gssDB.size(), gssRepository.size());

        // check if all GSs with same ID are equal
        for (GasStationDto gsDB : gssDB) {
            for (GasStationDto gasStationRepository : gssRepository) {
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
	
}