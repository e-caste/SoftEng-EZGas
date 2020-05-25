package it.polito.ezgas.repository;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GasStationRepositoryTests {
	@Autowired
	GasStationRepository gasStationRepository;
	
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
																			//id|car|dies_pr|gas_pr|gas_station_address|station_name|has_die|has_g|has_met|has_s|has_s_p|	lat	|	lon		|met_pr|r_dep|time|r_user|s_pr|s_p_pr|user_id
											"INSERT INTO GAS_STATION VALUES (1, 'bah', 1.375, 1.753, 'via Olanda, 12, Torino', 'Esso',  TRUE, TRUE, FALSE, TRUE, FALSE, 45.048903, 7.659812, 0,  		0, NULL, -1, 1.864, 0, NULL)",
            								"INSERT INTO GAS_STATION VALUES (2, 'Enjoy', 1.431, 1.658, 'via Spagna, 32, Torino', 'Eni', TRUE, TRUE, FALSE, TRUE, FALSE, 45.048903, 7.659812, 0, 		0,  NULL, -1, 1.854, 0, NULL)"

    );
    
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
		GS1carSharing = "bah";
		GS1.setDieselPrice(1.375);
		GS1.setGasPrice(1.753);
		GS1.setGasStationAddress("via Olanda, 12, Torino");
		GS1.setGasStationName(GS1Name);
		GS1.setHasDiesel(true);
		GS1.setHasGas(true);
		GS1.setHasMethane(false);
		GS1.setHasSuper(true);
		GS1.setHasSuperPlus(false);
		GS1.setMethanePrice(0);
		GS1.setReportDependability(0);
		GS1.setReportTimestamp(null);
		GS1.setReportUser(-1);
		GS1.setSuperPrice(1.864);
		GS1.setSuperPlusPrice(0);
		GS1.setGasStationId(GS1id);
		GS1.setLat(45.048903);
		GS1.setLon(7.659812);
		GS1.setCarSharing(GS1carSharing);
		//GS1Dto = GasStationConverter.convertEntityToDto(GS1);
		gasStationRepository.save(GS1);
		
		GS1Dto = new GasStationDto(1, "Esso", "via Olanda, 12, Torino", true, true, false, true, false, "bah", 45.048903, 7.659812, 1.375, 1.864, 0, 1.753, 0, -1, null, 0);
		GS3Dto = new GasStationDto(3, "Repsol", "via Portogallo, 43, Torino", true, true, false, true, false, "IShare", 45.0, 7.0, 1.375, 1.864, 0, 1.753, 0, -1, null, 0);
	}
	
	@Test
	public void test_findById() {
		//existing id
		GasStation gasStation = gasStationRepository.findById(GS1id);
		assertTrue(gasStation.equals(GS1));
		//nonExisting id
		gasStation = gasStationRepository.findById(1000);
		assertNull(gasStation);
	}
	
	@Test
	public void test_findByCarSharing() {
		//existing CarSharing
		List<GasStation> gasStation = gasStationRepository.findByCarSharing("bah");
		assertEquals(1, gasStation.size());
		assertTrue(gasStation.get(0).equals(GS1));
		//existing CarSharing
		 gasStation = gasStationRepository.findByCarSharing("Enjoy");
		 assertEquals(1, gasStation.size());
		 assertEquals("Eni", gasStation.get(0).getGasStationName());
		//nonExisting CarSharing
		gasStation = gasStationRepository.findByCarSharing("NonExistingCarSharing");
		assertEquals(0, gasStation.size());
	}
}
