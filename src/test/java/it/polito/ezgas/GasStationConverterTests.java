package it.polito.ezgas;

import static org.junit.Assert.*;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationConverterTests {

    GasStation gasStation;
    GasStationDto gasStationDto;

    // constants to initialize variables in setUp method
    Integer gasStationId = 10, reportUser = 42;
    String gasStationName = "name",
            getGasStationAddress = "Corso Duca degli Abruzzi 24",
            carSharing = "Enjoy",
            reportTimestamp = "1589707858";
    boolean hasDiesel = true,
            hasSuper = false,
            hasSuperPlus = true,
            hasGas = false,
            hasMethane = true;
    double lat = 45.06248474121094,
            lon = 7.662814140319824,
            reportDependability = 3.33,
            dieselPrice = 1.29,
            superPrice = 1.12,
            superPlusPrice = 1.18,
            gasPrice = 1.43,
            methanePrice = 1.12;
    User user = new User("username", "password", "em@i.l", 0);



}
