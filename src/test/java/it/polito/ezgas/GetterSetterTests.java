package it.polito.ezgas;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.PriceReport;
import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.GasStationDto;

public class GetterSetterTests {

    public static void main() {
        GetterSetterTests.priceReportDto();
        GetterSetterTests.idPw();
        GetterSetterTests.loginDto();
        GetterSetterTests.userDto();
        GetterSetterTests.gasStationDto();
        GetterSetterTests.gasStation();
        GetterSetterTests.user();
        GetterSetterTests.priceReport();
    }

    @Test
    public static void priceReportDto() {
        PriceReportDto priceReportDto = new PriceReportDto();

        priceReportDto.setPriceReportId(-572);
        assert priceReportDto.getPriceReportId().equals(-572);

        // TODO: understand why this test fails (even with the same user object) - maybe implement equals method
        priceReportDto.setUser(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));
        assert priceReportDto.getUser().equals(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));

        priceReportDto.setDieselPrice(3.14);
        assert priceReportDto.getDieselPrice() == 3.14;

        priceReportDto.setSuperPrice(-999999.999999);
        assert priceReportDto.getSuperPrice() == -999999.999999;

        priceReportDto.setSuperPlusPrice(-999999.999999);
        assert priceReportDto.getSuperPlusPrice() == -999999.999999;

        priceReportDto.setGasPrice(87348.58894);
        assert priceReportDto.getGasPrice() == 87348.58894;

    }

    @Test
    public static void idPw() {
        IdPw idPw = new IdPw();

        idPw.setUser("PoliTo");
        assert idPw.getUser().equals("PoliTo");

        idPw.setPw("string with a space");
        assert idPw.getPw().equals("string with a space");

    }

    @Test
    public static void loginDto() {
        LoginDto loginDto = new LoginDto();

        loginDto.setUserId(894);
        assert loginDto.getUserId().equals(894);

        loginDto.setUserName("string with a space");
        assert loginDto.getUserName().equals("string with a space");

        loginDto.setToken("test_string");
        assert loginDto.getToken().equals("test_string");

        loginDto.setEmail("string with a space");
        assert loginDto.getEmail().equals("string with a space");

        loginDto.setReputation(867);
        assert loginDto.getReputation().equals(867);

        loginDto.setAdmin(true);
        assert loginDto.getAdmin().equals(true);

    }

    @Test
    public static void userDto() {
        UserDto userDto = new UserDto();

        userDto.setUserId(941);
        assert userDto.getUserId().equals(941);

        userDto.setUserName("test_string");
        assert userDto.getUserName().equals("test_string");

        userDto.setPassword("string with a space");
        assert userDto.getPassword().equals("string with a space");

        userDto.setEmail("string with a space");
        assert userDto.getEmail().equals("string with a space");

        userDto.setReputation(872);
        assert userDto.getReputation().equals(872);

        userDto.setAdmin(true);
        assert userDto.getAdmin().equals(true);

    }

    @Test
    public static void gasStationDto() {
        GasStationDto gasStationDto = new GasStationDto();

        gasStationDto.setReportDependability(2.76);
        assert gasStationDto.getReportDependability() == 2.76;

        gasStationDto.setGasStationId(77);
        assert gasStationDto.getGasStationId().equals(77);

        gasStationDto.setGasStationName("PoliTo");
        assert gasStationDto.getGasStationName().equals("PoliTo");

        gasStationDto.setGasStationAddress("test_string");
        assert gasStationDto.getGasStationAddress().equals("test_string");

        gasStationDto.setHasDiesel(true);
        assert gasStationDto.getHasDiesel() == true;

        gasStationDto.setHasSuper(true);
        assert gasStationDto.getHasSuper().equals(true);

        gasStationDto.setHasSuperPlus(true);
        assert gasStationDto.getHasSuperPlus().equals(true);

        gasStationDto.setHasGas(false);
        assert gasStationDto.getHasGas().equals(false);

        gasStationDto.setLat(2.76);
        assert gasStationDto.getLat() == 2.76;

        gasStationDto.setLon(87348.58894);
        assert gasStationDto.getLon() == 87348.58894;

        gasStationDto.setDieselPrice(2.76);
        assert gasStationDto.getDieselPrice() == 2.76;

        gasStationDto.setSuperPrice(2.76);
        assert gasStationDto.getSuperPrice() == 2.76;

        gasStationDto.setSuperPlusPrice(-999999.999999);
        assert gasStationDto.getSuperPlusPrice() == -999999.999999;

        gasStationDto.setGasPrice(-999999.999999);
        assert gasStationDto.getGasPrice() == -999999.999999;

        gasStationDto.setPriceReportDtos(new ArrayList<PriceReportDto>(Arrays.asList(new PriceReportDto(-74,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),3.14,-1.0,-1.0,87348.58894),new PriceReportDto(291,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),3.14,2.76,2.76,87348.58894),new PriceReportDto(-133,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),2.76,2.76,3.14,87348.58894))));
        assert gasStationDto.getPriceReportDtos().equals(new ArrayList<PriceReportDto>(Arrays.asList(new PriceReportDto(-74,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),3.14,-1.0,-1.0,87348.58894),new PriceReportDto(291,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),3.14,2.76,2.76,87348.58894),new PriceReportDto(-133,new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924),2.76,2.76,3.14,87348.58894))));

        gasStationDto.setReportUser(478);
        assert gasStationDto.getReportUser().equals(478);

        gasStationDto.setReportTimestamp("string with a space");
        assert gasStationDto.getReportTimestamp().equals("string with a space");

        gasStationDto.setHasMethane(true);
        assert gasStationDto.getHasMethane() == true;

        gasStationDto.setMethanePrice(87348.58894);
        assert gasStationDto.getMethanePrice() == 87348.58894;

        gasStationDto.setCarSharing("§tr1n9 w/ @ w3|rD cHaRacter");
        assert gasStationDto.getCarSharing().equals("§tr1n9 w/ @ w3|rD cHaRacter");

    }

    @Test
    public static void gasStation() {
        GasStation gasStation = new GasStation();

        gasStation.setGasStationId(-81);
        assert gasStation.getGasStationId().equals(-81);

        gasStation.setGasStationName("test_string");
        assert gasStation.getGasStationName().equals("test_string");

        gasStation.setGasStationAddress("string with a space");
        assert gasStation.getGasStationAddress().equals("string with a space");

        gasStation.setReportDependability(3.14);
        assert gasStation.getReportDependability() == 3.14;

        gasStation.setReportUser(-720);
        assert gasStation.getReportUser().equals(-720);

        gasStation.setReportTimestamp("PoliTo");
        assert gasStation.getReportTimestamp().equals("PoliTo");

        gasStation.setHasDiesel(true);
        assert gasStation.getHasDiesel() == true;

        gasStation.setHasSuper(true);
        assert gasStation.getHasSuper() == true;

        gasStation.setHasSuperPlus(true);
        assert gasStation.getHasSuperPlus() == true;

        gasStation.setHasGas(false);
        assert gasStation.getHasGas() == false;

        gasStation.setLat(3.14);
        assert gasStation.getLat() == 3.14;

        gasStation.setLon(3.14);
        assert gasStation.getLon() == 3.14;

        gasStation.setDieselPrice(2.76);
        assert gasStation.getDieselPrice() == 2.76;

        gasStation.setSuperPrice(2.76);
        assert gasStation.getSuperPrice() == 2.76;

        gasStation.setSuperPlusPrice(3.14);
        assert gasStation.getSuperPlusPrice() == 3.14;

        gasStation.setGasPrice(-1.0);
        assert gasStation.getGasPrice() == -1.0;

        gasStation.setUser(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));
        assert gasStation.getUser().equals(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));

        gasStation.setHasMethane(true);
        assert gasStation.getHasMethane() == true;

        gasStation.setMethanePrice(87348.58894);
        assert gasStation.getMethanePrice() == 87348.58894;

        gasStation.setCarSharing("§tr1n9 w/ @ w3|rD cHaRacter");
        assert gasStation.getCarSharing().equals("§tr1n9 w/ @ w3|rD cHaRacter");

    }

    @Test
    public static void user() {
        User user = new User();

        user.setUserId(-557);
        assert user.getUserId().equals(-557);

        user.setUserName("string with a space");
        assert user.getUserName().equals("string with a space");

        user.setPassword("string with a space");
        assert user.getPassword().equals("string with a space");

        user.setEmail("PoliTo");
        assert user.getEmail().equals("PoliTo");

        user.setReputation(670);
        assert user.getReputation().equals(670);

        user.setAdmin(false);
        assert user.getAdmin().equals(false);

    }

    @Test
    public static void priceReport() {
        PriceReport priceReport = new PriceReport();

        priceReport.setUser(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));
        assert priceReport.getUser().equals(new User("string with a space", "§tr1n9 w/ @ w3|rD cHaRacter", "PoliTo", 924));

        priceReport.setDieselPrice(3.14);
        assert priceReport.getDieselPrice() == 3.14;

        priceReport.setSuperPrice(2.76);
        assert priceReport.getSuperPrice() == 2.76;

        priceReport.setSuperPlusPrice(3.14);
        assert priceReport.getSuperPlusPrice() == 3.14;

        priceReport.setGasPrice(87348.58894);
        assert priceReport.getGasPrice() == 87348.58894;

        priceReport.setPriceReportId(979);
        assert priceReport.getPriceReportId().equals(979);

    }
}