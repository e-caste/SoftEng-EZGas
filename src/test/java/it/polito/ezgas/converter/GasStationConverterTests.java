package it.polito.ezgas.converter;

import static org.junit.Assert.*;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GasStationConverterTests {

    GasStation gasStationAllFuels, gasStation;
    GasStationDto gasStationDtoAllFuels, gasStationDto;

    // constants to initialize variables in setUp method
    Integer gasStationId = 1,
            reportUser = 2;  // userId of User who submitted the last priceReport
    String gasStationName = "name",
           gasStationAddress = "Corso Duca degli Abruzzi 24",
           carSharing = "Enjoy",
           reportTimestamp = "1589707858";
    boolean hasDiesel = true,
            hasSuper = true,
            hasSuperPlus = true,
            hasGas = true,
            hasMethane = true,
            hasPremiumDiesel = true;
    double lat = 45.06248474121094,
           lon = 7.662814140319824,
           reportDependability = 3.33,
           dieselPrice = 1.29,
           superPrice = 1.12,
           superPlusPrice = 1.18,
           gasPrice = 1.43,
           methanePrice = 1.12,
           premiumDieselPrice = 1.43;

    @Before
    public void setUp() {
        gasStationAllFuels = new GasStation(
                gasStationName,
                gasStationAddress,
                hasDiesel,
                hasSuper,
                hasSuperPlus,
                hasGas,
                hasMethane,
                hasPremiumDiesel,
                carSharing,
                lat,
                lon,
                dieselPrice,
                superPrice,
                superPlusPrice,
                gasPrice,
                methanePrice,
                premiumDieselPrice,
                reportUser,
                reportTimestamp,
                reportDependability
        );
        gasStationAllFuels.setGasStationId(gasStationId);

        // instance with some null values
        gasStation = new GasStation();
        gasStation.setGasStationId(gasStationId);
        gasStation.setGasStationName(gasStationName);
        gasStation.setGasStationAddress(gasStationAddress);
        gasStation.setHasDiesel(true);
        gasStation.setDieselPrice(dieselPrice);

        gasStationDtoAllFuels = new GasStationDto(
                gasStationId,
                gasStationName,
                gasStationAddress,
                hasDiesel,
                hasSuper,
                hasSuperPlus,
                hasGas,
                hasMethane,
                hasPremiumDiesel,
                carSharing,
                lat,
                lon,
                dieselPrice,
                superPrice,
                superPlusPrice,
                gasPrice,
                methanePrice,
                premiumDieselPrice,
                reportUser,
                reportTimestamp,
                reportDependability
        );

        gasStationDto = new GasStationDto();
        gasStationDto.setGasStationId(gasStationId);
        gasStationDto.setGasStationName(gasStationName);
        gasStationDto.setGasStationAddress(gasStationAddress);
        gasStationDto.setHasDiesel(true);
        gasStationDto.setDieselPrice(dieselPrice);
    }

    @Test
    public void testConvertEntityToDto() {
        GasStationDto gsDtos = GasStationConverter.convertEntityToDto(gasStationAllFuels);
        assertTrue(gasStationDtoAllFuels.equals(gsDtos));
        assertTrue(gasStationDto.equals(GasStationConverter.convertEntityToDto(gasStation)));
    }

    @Test
    public void testConvertDtoToEntity() {
        GasStation gs = GasStationConverter.convertDtoToEntity(gasStationDtoAllFuels);
        assertTrue(gasStationAllFuels.equals(gs));
        assertTrue(gasStation.equals(GasStationConverter.convertDtoToEntity(gasStationDto)));
    }
}
