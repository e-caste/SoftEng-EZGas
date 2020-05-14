package it.polito.ezgas;

import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.entity.PriceReport;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.dto.PriceReportDto;
import org.junit.Test;

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

        priceReportDto.setPriceReportId(-112);
        assert priceReportDto.getPriceReportId().equals(-112);

        priceReportDto.setDieselPrice(-999999.999999);
        assert priceReportDto.getDieselPrice() == -999999.999999;

        priceReportDto.setSuperPrice(2.76);
        assert priceReportDto.getSuperPrice() == 2.76;

        priceReportDto.setSuperPlusPrice(-1.0);
        assert priceReportDto.getSuperPlusPrice() == -1.0;

        priceReportDto.setGasPrice(2.76);
        assert priceReportDto.getGasPrice() == 2.76;

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

        loginDto.setUserId(392);
        assert loginDto.getUserId().equals(392);

        loginDto.setUserName("PoliTo");
        assert loginDto.getUserName().equals("PoliTo");

        loginDto.setToken("PoliTo");
        assert loginDto.getToken().equals("PoliTo");

        loginDto.setEmail("PoliTo");
        assert loginDto.getEmail().equals("PoliTo");

        loginDto.setReputation(121);
        assert loginDto.getReputation().equals(121);

        loginDto.setAdmin(true);
        assert loginDto.getAdmin().equals(true);

    }

    @Test
    public static void userDto() {
        UserDto userDto = new UserDto();

        userDto.setUserId(485);
        assert userDto.getUserId().equals(485);

        userDto.setUserName("§tr1n9 w/ @ w3|rD cHaRacter");
        assert userDto.getUserName().equals("§tr1n9 w/ @ w3|rD cHaRacter");

        userDto.setPassword("string with a space");
        assert userDto.getPassword().equals("string with a space");

        userDto.setEmail("string with a space");
        assert userDto.getEmail().equals("string with a space");

        userDto.setReputation(662);
        assert userDto.getReputation().equals(662);

        userDto.setAdmin(true);
        assert userDto.getAdmin().equals(true);

    }

    @Test
    public static void gasStationDto() {
        GasStationDto gasStationDto = new GasStationDto();

        gasStationDto.setReportDependability(2.76);
        assert gasStationDto.getReportDependability() == 2.76;

        gasStationDto.setGasStationId(-433);
        assert gasStationDto.getGasStationId().equals(-433);

        gasStationDto.setGasStationName("test_string");
        assert gasStationDto.getGasStationName().equals("test_string");

        gasStationDto.setGasStationAddress("PoliTo");
        assert gasStationDto.getGasStationAddress().equals("PoliTo");

        gasStationDto.setHasDiesel(true);
        assert gasStationDto.getHasDiesel() == true;

        gasStationDto.setHasSuper(false);
        assert gasStationDto.getHasSuper().equals(false);

        gasStationDto.setHasSuperPlus(true);
        assert gasStationDto.getHasSuperPlus().equals(true);

        gasStationDto.setHasGas(true);
        assert gasStationDto.getHasGas().equals(true);

        gasStationDto.setLat(2.76);
        assert gasStationDto.getLat() == 2.76;

        gasStationDto.setLon(2.76);
        assert gasStationDto.getLon() == 2.76;

        gasStationDto.setDieselPrice(3.14);
        assert gasStationDto.getDieselPrice() == 3.14;

        gasStationDto.setSuperPrice(87348.58894);
        assert gasStationDto.getSuperPrice() == 87348.58894;

        gasStationDto.setSuperPlusPrice(2.76);
        assert gasStationDto.getSuperPlusPrice() == 2.76;

        gasStationDto.setGasPrice(-1.0);
        assert gasStationDto.getGasPrice() == -1.0;

        gasStationDto.setReportUser(-341);
        assert gasStationDto.getReportUser().equals(-341);

        gasStationDto.setReportTimestamp("string with a space");
        assert gasStationDto.getReportTimestamp().equals("string with a space");

        gasStationDto.setHasMethane(true);
        assert gasStationDto.getHasMethane() == true;

        gasStationDto.setMethanePrice(2.76);
        assert gasStationDto.getMethanePrice() == 2.76;

        gasStationDto.setCarSharing("string with a space");
        assert gasStationDto.getCarSharing().equals("string with a space");

    }

    @Test
    public static void gasStation() {
        GasStation gasStation = new GasStation();

        gasStation.setGasStationId(-439);
        assert gasStation.getGasStationId().equals(-439);

        gasStation.setGasStationName("string with a space");
        assert gasStation.getGasStationName().equals("string with a space");

        gasStation.setGasStationAddress("§tr1n9 w/ @ w3|rD cHaRacter");
        assert gasStation.getGasStationAddress().equals("§tr1n9 w/ @ w3|rD cHaRacter");

        gasStation.setReportDependability(87348.58894);
        assert gasStation.getReportDependability() == 87348.58894;

        gasStation.setReportUser(-265);
        assert gasStation.getReportUser().equals(-265);

        gasStation.setReportTimestamp("string with a space");
        assert gasStation.getReportTimestamp().equals("string with a space");

        gasStation.setHasDiesel(true);
        assert gasStation.getHasDiesel() == true;

        gasStation.setHasSuper(true);
        assert gasStation.getHasSuper() == true;

        gasStation.setHasSuperPlus(false);
        assert gasStation.getHasSuperPlus() == false;

        gasStation.setHasGas(false);
        assert gasStation.getHasGas() == false;

        gasStation.setLat(-1.0);
        assert gasStation.getLat() == -1.0;

        gasStation.setLon(87348.58894);
        assert gasStation.getLon() == 87348.58894;

        gasStation.setDieselPrice(3.14);
        assert gasStation.getDieselPrice() == 3.14;

        gasStation.setSuperPrice(87348.58894);
        assert gasStation.getSuperPrice() == 87348.58894;

        gasStation.setSuperPlusPrice(-1.0);
        assert gasStation.getSuperPlusPrice() == -1.0;

        gasStation.setGasPrice(-1.0);
        assert gasStation.getGasPrice() == -1.0;

        gasStation.setHasMethane(false);
        assert gasStation.getHasMethane() == false;

        gasStation.setMethanePrice(-999999.999999);
        assert gasStation.getMethanePrice() == -999999.999999;

        gasStation.setCarSharing("PoliTo");
        assert gasStation.getCarSharing().equals("PoliTo");

    }

    @Test
    public static void user() {
        User user = new User();

        user.setUserId(-799);
        assert user.getUserId().equals(-799);

        user.setUserName("string with a space");
        assert user.getUserName().equals("string with a space");

        user.setPassword("string with a space");
        assert user.getPassword().equals("string with a space");

        user.setEmail("§tr1n9 w/ @ w3|rD cHaRacter");
        assert user.getEmail().equals("§tr1n9 w/ @ w3|rD cHaRacter");

        user.setReputation(-612);
        assert user.getReputation().equals(-612);

        user.setAdmin(true);
        assert user.getAdmin().equals(true);

    }

    @Test
    public static void priceReport() {
        PriceReport priceReport = new PriceReport();

        priceReport.setDieselPrice(87348.58894);
        assert priceReport.getDieselPrice() == 87348.58894;

        priceReport.setSuperPrice(2.76);
        assert priceReport.getSuperPrice() == 2.76;

        priceReport.setSuperPlusPrice(-999999.999999);
        assert priceReport.getSuperPlusPrice() == -999999.999999;

        priceReport.setGasPrice(2.76);
        assert priceReport.getGasPrice() == 2.76;

        priceReport.setPriceReportId(200);
        assert priceReport.getPriceReportId().equals(200);

    }
}