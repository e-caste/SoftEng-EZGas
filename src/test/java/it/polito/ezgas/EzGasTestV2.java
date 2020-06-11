package it.polito.ezgas;

import exception.*;
import it.polito.ezgas.controller.UserController;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.GasStationService;
import it.polito.ezgas.service.UserService;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class EzGasTestV2 extends TestCase {
    @Autowired
    private GasStationService gasStationService;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GasStationRepository gasStationRepository;


    private List<Integer> userToDelete = new ArrayList<>();
    private List<Integer> gasStationToDelete = new ArrayList<>();
    private UserDto testUser;
    private UserDto testAdmin;
    private IdPw credentials;
    private IdPw credentialsAdmin;
    private GasStationDto testGasStation;
    private GasStationDto testGasStationNoPriceList;
    private GasStationDto testFarGasStation;

    private String testName = "test";
    private String testEmail = "test@test.test";
    private String testPwd = "testPwd";
    private String testNameAdmin = "test_Admin";
    private String testEmailAdmin = "test_Admin@test.test";
    private String testPwdAdmin = "testPwd_Admin";
    private Integer testReputation = 0;
    private String testGasStationName = "testGasStation";
    private String testGasStationName2 = "testGasStation2";

    private String testGasStationAddress = "testAddress";
    private double testLat = 60.0;
    private double testLon = 60.0;
    private int testRadius = 5;
    private String testGasStationAddress2 = "testAddress2";
    private double testLat2 = 60.005;
    private double testLon2 = 60.005;
    private double testFarLat = 80.00;
    private double testFarLon = 80.00;

    private String testCarSharing2 = "Enjoy";
    private Double missingPrice = null;
    private boolean testHasDiesel = true;
    private double testDiesel = 1.2;
    private boolean testHasSuper = true;
    private double testSuper = 1.4;
    private boolean testHasSuperPlus = true;
    private double testSuperPlus = 1.45;
    private boolean testHasGas = true;
    private double testGas = 0.9;
    private boolean testHasMethane = true;
    private double testMethane = 0.9;
    private boolean testHasPremiumDiesel = true;
    private double testPremiumDiesel = 1.6;
    private String testCarSharing = "Car2Go";

    @Before
    public void init() {
        userToDelete.clear();
        gasStationToDelete.clear();
        System.out.println("---------------------INIT---------------------");
        testUser = new UserDto();
        testUser.setPassword(testPwd);
        testUser.setEmail(testEmail);
        testUser.setUserName(testName);
        testUser.setAdmin(false);
        testUser.setReputation(testReputation);

        testAdmin = new UserDto();
        testAdmin.setUserName(testNameAdmin);
        testAdmin.setEmail(testEmailAdmin);
        testAdmin.setPassword(testPwdAdmin);
        testAdmin.setAdmin(Boolean.TRUE);
        testAdmin.setReputation(0);
        credentials = new IdPw();
        credentials.setUser(testEmail);
        credentials.setPw(testPwd);

        credentialsAdmin = new IdPw();
        credentialsAdmin.setUser(testEmailAdmin);
        credentialsAdmin.setPw(testPwdAdmin);

        testGasStation = new GasStationDto();
        testGasStation.setGasStationName(testGasStationName);
        testGasStation.setGasStationAddress(testGasStationAddress);
        testGasStation.setLat(testLat);
        testGasStation.setLon(testLon);
        testGasStation.setHasDiesel(testHasDiesel);
        testGasStation.setDieselPrice(testDiesel);
        testGasStation.setHasSuper(testHasSuper);
        testGasStation.setSuperPrice(testSuper);
        testGasStation.setHasSuperPlus(testHasSuperPlus);
        testGasStation.setSuperPlusPrice(testSuperPlus);
        testGasStation.setHasGas(testHasGas);
        testGasStation.setGasPrice(testGas);
        testGasStation.setHasMethane(testHasMethane);
        testGasStation.setMethanePrice(testMethane);
        testGasStation.setHasPremiumDiesel(testHasPremiumDiesel);
        testGasStation.setPremiumDieselPrice(testPremiumDiesel);
        testGasStation.setCarSharing(testCarSharing);

        testFarGasStation = new GasStationDto();
        testFarGasStation.setGasStationName(testGasStationName);
        testFarGasStation.setGasStationAddress(testGasStationAddress);
        testFarGasStation.setLat(testFarLat);
        testFarGasStation.setLon(testFarLon);
        testFarGasStation.setHasDiesel(testHasDiesel);
        testFarGasStation.setDieselPrice(testDiesel);
        testFarGasStation.setHasSuper(testHasSuper);
        testFarGasStation.setSuperPrice(testSuper);
        testFarGasStation.setHasSuperPlus(testHasSuperPlus);
        testFarGasStation.setSuperPlusPrice(testSuperPlus);
        testFarGasStation.setHasGas(testHasGas);
        testFarGasStation.setGasPrice(testGas);
        testFarGasStation.setHasMethane(testHasMethane);
        testFarGasStation.setMethanePrice(testMethane);
        testFarGasStation.setHasPremiumDiesel(testHasPremiumDiesel);
        testFarGasStation.setPremiumDieselPrice(testPremiumDiesel);
        testFarGasStation.setCarSharing(testCarSharing);

        testGasStationNoPriceList = new GasStationDto();
        testGasStationNoPriceList.setGasStationName(testGasStationName2);
        testGasStationNoPriceList.setGasStationAddress(testGasStationAddress2);
        testGasStationNoPriceList.setLat(testLat2);
        testGasStationNoPriceList.setLon(testLon2);
        testGasStationNoPriceList.setHasDiesel(testHasDiesel);
        testGasStationNoPriceList.setDieselPrice(missingPrice);
        testGasStationNoPriceList.setHasSuper(testHasSuper);
        testGasStationNoPriceList.setSuperPrice(missingPrice);
        testGasStationNoPriceList.setHasSuperPlus(testHasSuperPlus);
        testGasStationNoPriceList.setSuperPlusPrice(missingPrice);
        testGasStationNoPriceList.setHasGas(testHasGas);
        testGasStationNoPriceList.setGasPrice(missingPrice);
        testGasStationNoPriceList.setHasMethane(testHasMethane);
        testGasStationNoPriceList.setMethanePrice(missingPrice);
        testGasStationNoPriceList.setHasPremiumDiesel(testHasPremiumDiesel);
        testGasStationNoPriceList.setPremiumDieselPrice(missingPrice);
        testGasStationNoPriceList.setCarSharing(testCarSharing2);

    }

    @After
    public final void after() {
        UserDto superadmin = new UserDto(null,"TESTSUPERADMIN","TESTSUPERADMIN","testsuper@admin.com",0,true);
        superadmin = userService.saveUser(superadmin);
        IdPw superAdminCred = new IdPw();
        superAdminCred.setUser("testsuper@admin.com");
        superAdminCred.setPw("TESTSUPERADMIN");
        try {
            LoginDto login = userService.login(superAdminCred);
            if(userToDelete.size() > 0){
                for(int id : userToDelete){
                    userRepository.delete(id);
                }
            }
            if(gasStationToDelete.size() > 0){
                for(int id : gasStationToDelete){
                    gasStationRepository.delete(id);
                }
            }
            userRepository.delete(superadmin.getUserId());
        } catch (InvalidLoginDataException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1
     */
    @Test
    public void testCreateUserAccount() {
        UserDto res = userService.saveUser(testUser);

        userToDelete.add(res.getUserId());

        assertEquals(testEmail, res.getEmail());
        assertEquals(testPwd, res.getPassword());
        assertEquals(testName, res.getUserName());
        assertEquals(new Integer(0), res.getReputation());
        assertFalse(res.getAdmin());
    }


    /**
     * FR 1.1
     */
    @Test
    public void testCreateUserAccountAdmin() {
        testUser.setAdmin(true);
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(user.getUserId());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getUserName(), user.getUserName());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertTrue(user.getAdmin());
        assertEquals(new Integer(0), user.getReputation());
    }

    /**
     * FR 1.1,1.2
     */
    @Test
    public void testCreateUserAccountAdminTwice() {
        testUser.setAdmin(true);
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(user.getUserId());
        List<UserDto> before = userService.getAllUsers();
        user = userService.saveUser(testAdmin);
        userToDelete.add(user.getUserId());
        List<UserDto> after = userService.getAllUsers();
        assertEquals(before.size() + 1, after.size());
    }


    /**
     * FR 1.1,1.2
     */
    @Test
    public void testDeleteUserAccount(){
        UserDto user = userService.saveUser(testUser);
        try{
            LoginDto log = userService.login(credentials);
            assertTrue(userService.deleteUser(user.getUserId()));

        } catch (InvalidLoginDataException | InvalidUserException e) {
            e.printStackTrace();
            userToDelete.add(user.getUserId());
            fail();
        }
    }

    /**
     * FR 1.1,1.2
     */
    @Test
    public void testDeleteUserAccountAdmin(){
        UserDto user = userService.saveUser(testUser);
        UserDto admin = userService.saveUser(testAdmin);
        try{
            LoginDto log = userService.login(credentialsAdmin);
            assertTrue(userService.deleteUser(user.getUserId()));

            assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | InvalidUserException e) {
            e.printStackTrace();
            userToDelete.add(user.getUserId());
            userToDelete.add(admin.getUserId());
            fail();
        }
    }


    /**
     * FR 1.1,3,3.1
     */
    @Test
    public void testCreateGasStation() {
        UserDto admin = userService.saveUser(testAdmin);

        try {
            LoginDto res = userService.login(credentialsAdmin);

            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            userToDelete.add(admin.getUserId());
            gasStationToDelete.add(stationDto.getGasStationId());
            assertEquals(testGasStation.getGasStationName(), stationDto.getGasStationName());
            assertEquals(testGasStation.getGasStationAddress(), stationDto.getGasStationAddress());
            assertEquals(testGasStation.getLat(), stationDto.getLat());
            assertEquals(testGasStation.getLon(), stationDto.getLon());
            assertEquals(testGasStation.getCarSharing(), stationDto.getCarSharing());
            assertEquals(testGasStation.getHasDiesel(), stationDto.getHasDiesel());
            assertEquals(testGasStation.getHasSuper(), stationDto.getHasSuper());
            assertEquals(testGasStation.getHasSuperPlus(), stationDto.getHasSuperPlus());
            assertEquals(testGasStation.getHasGas(), stationDto.getHasGas());
            assertEquals(testGasStation.getHasMethane(), stationDto.getHasMethane());
            assertEquals(testGasStation.getHasPremiumDiesel(), stationDto.getHasPremiumDiesel());
            assertEquals(testGasStation.getDieselPrice(), stationDto.getDieselPrice());
            assertEquals(testGasStation.getSuperPrice(), stationDto.getSuperPrice());
            assertEquals(testGasStation.getSuperPlusPrice(), stationDto.getSuperPlusPrice());
            assertEquals(testGasStation.getGasPrice(), stationDto.getGasPrice());
            assertEquals(testGasStation.getMethanePrice(), stationDto.getMethanePrice());
            assertEquals(testGasStation.getPremiumDieselPrice(),stationDto.getPremiumDieselPrice());

        } catch (InvalidLoginDataException | GPSDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testCreateGasStationGPSNegativeLon() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            testGasStation.setLon( -190.00 );
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            //userService.deleteUser(admin.getUserId());
        } catch (InvalidLoginDataException  | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testCreateGasStationGPSPositiveLon() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            testGasStation.setLon( 190.00 );
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            //userService.deleteUser(admin.getUserId());
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testCreateGasStationGPSNegativeLat() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            testGasStation.setLat( -100.00 );
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            //userService.deleteUser(admin.getUserId());
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }
    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testCreateGasStationGPSPositiveLat() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            testGasStation.setLat( 100.00 );
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            //userService.deleteUser(admin.getUserId());
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * FR 1.1,3,3.1,3.3
     */
    @Test
    public void testModifyGasStation(){
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try{
            userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            stationDto.setGasStationName("newName");
            stationDto.setLon(0.0);
            stationDto.setLat(0.0);
            stationDto.setMethanePrice(1.1);
            stationDto.setGasPrice(1.1);
            stationDto.setDieselPrice(1.1);
            stationDto.setSuperPlusPrice(1.1);
            stationDto.setSuperPrice(1.1);
            stationDto.setPremiumDieselPrice(1.1);
            stationDto.setCarSharing("null");
            List<GasStationDto> before = gasStationService.getAllGasStations();
            GasStationDto modified = gasStationService.saveGasStation(stationDto);
            List<GasStationDto> after = gasStationService.getAllGasStations();
            assertEquals(before.size(),after.size());
            assertEquals(stationDto.getGasStationId(),modified.getGasStationId());
            assertEquals(0.0,modified.getLat());
            assertEquals(0.0,modified.getLon());
            assertEquals(1.1,modified.getGasPrice());
            assertEquals(1.1,modified.getMethanePrice());
            assertEquals(1.1,modified.getSuperPrice());
            assertEquals(1.1,modified.getSuperPlusPrice());
            assertEquals(1.1,modified.getDieselPrice());
            assertEquals(1.1,modified.getPremiumDieselPrice());
            assertEquals("newName",modified.getGasStationName());
            assertNull(modified.getCarSharing());
            gasStationToDelete.add(stationDto.getGasStationId());
            //assertTrue(gasStationService.deleteGasStation(modified.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | PriceException  e) {
            e.printStackTrace();
            fail();
        }
    }
    
    

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testModifyGasStationGPSNegativeLon() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            stationDto.setLon( -190.00 );
            GasStationDto modifiedDto = gasStationService.saveGasStation(stationDto);
            //assertTrue(gasStationService.deleteGasStation(modifiedDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testModifyGasStationGPSPositiveLon() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            stationDto.setLon( 190.00 );
            GasStationDto modifiedDto = gasStationService.saveGasStation(stationDto);
            //assertTrue(gasStationService.deleteGasStation(modifiedDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testModifyGasStationGPSNegativeLat() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            stationDto.setLat( -100.00 );
            GasStationDto modifiedDto = gasStationService.saveGasStation(stationDto);
            //assertTrue(gasStationService.deleteGasStation(modifiedDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1
     */
    @Test(expected = GPSDataException.class)
    public void testModifyGasStationGPSPositiveLat() throws GPSDataException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            stationDto.setLat( 100.00 );
            GasStationDto modifiedDto = gasStationService.saveGasStation(stationDto);
            //assertTrue(gasStationService.deleteGasStation(modifiedDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.2,3.3
     */
    @Test
    public void testDeleteGasStation(){
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            List<GasStationDto> before = gasStationService.getAllGasStations();
            assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            List<GasStationDto> after = gasStationService.getAllGasStations();
            assertEquals(before.size()-1,after.size());
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | PriceException | InvalidGasStationException | GPSDataException e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * FR 1.1,3,3.1,3.2,3.3
     */
    @Test(expected = InvalidGasStationException.class)
    public void testDeleteGasStationNegativeId() throws InvalidGasStationException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            List<GasStationDto> before = gasStationService.getAllGasStations();
            gasStationService.deleteGasStation(-10);
            List<GasStationDto> after = gasStationService.getAllGasStations();
            assertEquals(before.size(), after.size());
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException  e) {
            e.printStackTrace();
            fail();
        }
    }
    /*
    @Test
    public void testDeleteGasStationNotAdmin(){
        UserDto admin = userService.saveUser(testAdmin);
        UserDto user = userService.saveUser(testUser);
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            assertTrue(userService.deleteUser(admin.getUserId()));
            res = userService.login(credentials);
            List<GasStationDto> before = gasStationService.getAllGasStations();

            assertFalse(gasStationService.deleteGasStation(stationDto.getGasStationId()));

            List<GasStationDto> after = gasStationService.getAllGasStations();
            assertEquals(before.size(), after.size());
            assertTrue(userService.deleteUser(user.getUserId()));

        } catch (InvalidLoginDataException | InvalidUserException | InvalidGasStationException | GPSDataException | PriceException e) {
            e.printStackTrace();
            fail();
        }
    }
   */

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test
    @Transactional
    public void testReportFuelPrice(){
        UserDto admin = userService.saveUser(testAdmin);
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(admin.getUserId());
        userToDelete.add(user.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);
            //assertTrue(userService.deleteUser(admin.getUserId()));
            gasStationToDelete.add(stationDto.getGasStationId());
            res = userService.login(credentials);

            DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
            Date date = new Date(System.currentTimeMillis());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    user.getUserId());
            GasStationDto modifiedDto = gasStationService.getGasStationById(stationDto.getGasStationId());
            assertEquals(2.0,modifiedDto.getMethanePrice());
            assertEquals(2.0,modifiedDto.getGasPrice());
            assertEquals(2.0,modifiedDto.getDieselPrice());
            assertEquals(2.0,modifiedDto.getSuperPlusPrice());
            assertEquals(2.0,modifiedDto.getSuperPrice());
            assertEquals(2.0,modifiedDto.getPremiumDieselPrice());
            assertEquals(user.getUserId(),modifiedDto.getUserDto().getUserId());
            assertEquals(formatter.format(date),modifiedDto.getReportTimestamp());
        } catch (GPSDataException | PriceException | InvalidUserException | InvalidLoginDataException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test
    @Transactional
    public void testReportFuelPriceExistingPriceList(){
        UserDto admin = userService.saveUser(testAdmin);
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(admin.getUserId());
        userToDelete.add(user.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStation);
            //assertTrue(userService.deleteUser(admin.getUserId()));
            gasStationToDelete.add(stationDto.getGasStationId());
            res = userService.login(credentials);

            DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
            Date date = new Date(System.currentTimeMillis());
            gasStationService.setReport(stationDto.getGasStationId(),2.0,2.0,2.0,2.0,2.0,2.0,user.getUserId());
            GasStationDto modifiedDto = gasStationService.getGasStationById(stationDto.getGasStationId());
            assertEquals(2.0,modifiedDto.getMethanePrice());
            assertEquals(2.0,modifiedDto.getGasPrice());
            assertEquals(2.0,modifiedDto.getDieselPrice());
            assertEquals(2.0,modifiedDto.getSuperPlusPrice());
            assertEquals(2.0,modifiedDto.getSuperPrice());
            assertEquals(2.0,modifiedDto.getPremiumDieselPrice());
            assertEquals(user.getUserId(),modifiedDto.getUserDto().getUserId());

            assertEquals(formatter.format(date),modifiedDto.getReportTimestamp());
        } catch (GPSDataException | PriceException | InvalidUserException | InvalidLoginDataException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = InvalidGasStationException.class)
    public void testReportFuelPriceInvalidGasStation() throws InvalidGasStationException{
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(user.getUserId());

        try{
            LoginDto res = userService.login(credentials);

            gasStationService.setReport(-1,2.0,2.0,2.0,2.0,2.0,2.0,user.getUserId());

            //assertTrue(userService.deleteUser(user.getUserId()));
        } catch (PriceException | InvalidUserException | InvalidLoginDataException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = InvalidUserException.class)
    public void testReportFuelPriceInvalidUser() throws InvalidUserException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),2.0,2.0,2.0,2.0,2.0,2.0,-1);

            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (PriceException | InvalidGasStationException | InvalidLoginDataException | GPSDataException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = PriceException.class)
    @Transactional
    public void testReportFuelPriceInvalidDiesel() throws PriceException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    -10.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    admin.getUserId());

            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = PriceException.class)
    @Transactional
    public void testReportFuelPriceInvalidSuper() throws PriceException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    -10.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    admin.getUserId());

            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = PriceException.class)
    @Transactional
    public void testReportFuelPriceInvalidSuperPlus() throws PriceException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    2.0,
                    -10.0,
                    2.0,
                    2.0,
                    2.0,
                    admin.getUserId());

            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = PriceException.class)
    @Transactional
    public void testReportFuelPriceInvalidGas() throws PriceException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    2.0,
                    2.0,
                    -10.0,
                    2.0,
                    2.0,
                    admin.getUserId());

            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,3,3.1,3.3,4,5,5.1
     */
    @Test(expected = PriceException.class)
    @Transactional
    public void testReportFuelPriceInvalidMethane() throws PriceException{
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());

        try{
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);

            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    -10.0,
                    2.0,
                    admin.getUserId());
            //assertTrue(gasStationService.deleteGasStation(stationDto.getGasStationId()));
            //assertTrue(userService.deleteUser(admin.getUserId()));
        } catch (InvalidLoginDataException | GPSDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }






    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceGeographicAreaInvalidLatNegative() throws GPSDataException{
        List<GasStationDto> list = gasStationService.getGasStationsByProximity(-100,testLon,testRadius);
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceGeographicAreaInvalidLatPositive() throws GPSDataException{
        List<GasStationDto> list = gasStationService.getGasStationsByProximity(100,testLon,testRadius);
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceGeographicAreaInvalidLonNegative() throws GPSDataException{
        List<GasStationDto> list = gasStationService.getGasStationsByProximity(testLat,-190,testRadius);
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceGeographicAreaInvalidLonPositive() throws GPSDataException{
        List<GasStationDto> list = gasStationService.getGasStationsByProximity(testLat,190,testRadius);
        fail();
    }


    @Test(expected = InvalidGasTypeException.class)
    @Transactional
    public void testObtainFuelPriceFuelType() throws InvalidGasTypeException {
        UserDto admin = userService.saveUser(testAdmin);
        userToDelete.add(admin.getUserId());
        try {
            LoginDto res = userService.login(credentialsAdmin);
            GasStationDto stationDto = gasStationService.saveGasStation(testGasStationNoPriceList);
            GasStationDto stationDto1 = gasStationService.saveGasStation(testGasStation);
            gasStationToDelete.add(stationDto.getGasStationId());
            gasStationToDelete.add(stationDto1.getGasStationId());
            gasStationService.setReport(stationDto.getGasStationId(),
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    admin.getUserId());
            gasStationService.setReport(stationDto1.getGasStationId(),
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    2.0,
                    admin.getUserId());

            GasStationDto modifiedDto = gasStationService.getGasStationById(stationDto.getGasStationId());
            GasStationDto modifiedDto1 = gasStationService.getGasStationById(stationDto1.getGasStationId());

            //assertTrue(userService.deleteUser(admin.getUserId()));

            List<GasStationDto> list = gasStationService.getGasStationsByGasolineType("Not Existing");
        } catch (GPSDataException | PriceException | InvalidLoginDataException | InvalidUserException | InvalidGasStationException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test(expected = InvalidGasTypeException.class)
    public void testObtainFuelPriceWithCoordinatesInvalidFuel() throws InvalidGasTypeException {
        try {
            List<GasStationDto> list = gasStationService.getGasStationsWithCoordinates(testLat,testLon,testRadius,"Not Existing",testCarSharing);
        } catch (GPSDataException | InvalidCarSharingException e) {
            e.printStackTrace();
        }
        fail();
    }


    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceWithCoordinatesInvalidLatNegative() throws GPSDataException {
        try {
            List<GasStationDto> list = gasStationService.getGasStationsWithCoordinates(-100,testLon,testRadius,"null","null");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            e.printStackTrace();
        }
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceWithCoordinatesInvalidLatPositive() throws GPSDataException {
        try {
            List<GasStationDto> list = gasStationService.getGasStationsWithCoordinates(100,testLon,testRadius,"null","null");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            e.printStackTrace();
        }
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceWithCoordinatesInvalidLonNegative() throws GPSDataException {
        try {
            List<GasStationDto> list = gasStationService.getGasStationsWithCoordinates(testLat,-190,testRadius,"null","null");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            e.printStackTrace();
        }
        fail();
    }

    @Test(expected = GPSDataException.class)
    public void testObtainFuelPriceWithCoordinatesInvalidLonPositive() throws GPSDataException {
        try {
            List<GasStationDto> list = gasStationService.getGasStationsWithCoordinates(testLat,190,testRadius,"null","null");
        } catch (InvalidGasTypeException | InvalidCarSharingException e) {
            e.printStackTrace();
        }
        fail();
    }


    /**
     * FR 1.1,5.3
     */
    @Test
    @Transactional
    public void testIncreaseUserReputation(){
        UserDto user = userService.saveUser(testUser);
        UserDto user2 = new UserDto();
        user2.setPassword("user2");
        user2.setUserName("user2");
        user2.setEmail("user2@user.usr");
        user2.setAdmin(false);
        user2.setReputation(testReputation);
        user2 = userService.saveUser(user2);
        userToDelete.add(user.getUserId());
        userToDelete.add(user2.getUserId());
        try{
            int id2 = user2.getUserId();
            LoginDto res = userService.login(credentials);
            int rep;
            for(rep = 1; rep <=5; rep++){
                int rep2 = userService.increaseUserReputation(id2);
                assertEquals(rep,rep2);
            }

            int rep2 = userService.increaseUserReputation(id2);
            assertEquals(5,rep2);
        } catch (InvalidLoginDataException | InvalidUserException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,5.3
     */
    @Test (expected = InvalidUserException.class)
    public void testIncreaseUserReputationInvalid() throws InvalidUserException{
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(user.getUserId());
        try{
            LoginDto res = userService.login(credentials);
            userService.increaseUserReputation(-5);
        } catch (InvalidLoginDataException e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * FR 1.1,5.3
     */
    @Test
    @Transactional
    public void testDecreaseUserReputation(){
        UserDto user = userService.saveUser(testUser);
        UserDto user2 = new UserDto();
        user2.setPassword("user2");
        user2.setUserName("user2");
        user2.setEmail("user2@user.usr");
        user2.setAdmin(false);
        user2.setReputation(testReputation);
        user2 = userService.saveUser(user2);
        userToDelete.add(user.getUserId());
        userToDelete.add(user2.getUserId());

        try{
            int id2 = user2.getUserId();
            LoginDto res = userService.login(credentials);
            int rep,rep2 = 0;
            for(rep = 1; rep <=5; rep++){
                rep2 = userService.increaseUserReputation(id2);
            }
            assertEquals(5,rep2);

            for(rep = 4; rep >=-5; rep--){
                rep2 = userService.decreaseUserReputation(id2);
                assertEquals(rep,rep2);
            }
            rep2 = userService.decreaseUserReputation(id2);
            assertEquals(-5,rep2);
        } catch (InvalidLoginDataException | InvalidUserException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * FR 1.1,5.3
     */
    @Test (expected = InvalidUserException.class)
    public void testDecreaseUserReputationInvalid() throws InvalidUserException{
        UserDto user = userService.saveUser(testUser);
        userToDelete.add(user.getUserId());

        try{
            LoginDto res = userService.login(credentials);
            userService.decreaseUserReputation(-5);
        } catch (InvalidLoginDataException e) {
            e.printStackTrace();
            fail();
        }
    }


    private double computeDistance(double lon1,double lat1,double lon2,double lat2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            return dist * 60 * 1.1515 * 1.609344;
        }
    }

    private double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double computeDependability(GasStationDto g, long ts) throws ParseException {
        /*Integer reputation = g.getUserDto().getReputation();
        DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
        Date date = formatter.parse(g.getReportTimestamp());
        Long timestamp = date.getTime();
        //long timestamp = Long.parseLong(g.getReportTimestamp());
        //long now = System.currentTimeMillis();

        long diff = ts - timestamp;
        double obsolescence = 0.0;
        if (diff <= 604800000)
            obsolescence = 1 - (double)(diff)/7;

        return 50*(double)(reputation+5)/10 + 50*obsolescence;*/

        double rep_factor = 0.5;
        double timestamp_factor = 0.5; // equal weight
        Integer userReputation = g.getUserDto().getReputation();
        double rep_points = (userReputation + 5) / 10.0 * 100; // 0 for reputation -5, 100 for reputation +5
        DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
        Date date = formatter.parse(g.getReportTimestamp());
        Long timestamp = date.getTime();

        double timestamp_points = (1 - ((double)ts - timestamp) / 6.048e+8) * 100; // 100 if
        // now, 0 if
        // one week
        // (or more)
        // ago
        if (timestamp_points < 0)
            timestamp_points = 0.0;

        return round(rep_points * rep_factor + timestamp_points * timestamp_factor, 2);
    }

    private String timestampToDate(long timestamp) {

        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");
        return formatter.format(date);

    }
}