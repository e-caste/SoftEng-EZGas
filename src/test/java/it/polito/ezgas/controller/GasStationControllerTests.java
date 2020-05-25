package it.polito.ezgas.controller;


import exception.GPSDataException;
import exception.InvalidGasStationException;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.impl.GasStationServiceimpl;
import it.polito.ezgas.repository.GasStationRepository;

import it.polito.ezgas.service.GasStationService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

<<<<<<< HEAD
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.polito.ezgas.utils.Constants;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//@RunWith(SpringRunner.class)
//@SpringBootTest

@ActiveProfiles("test")

@WebMvcTest(controllers = GasStationController)
=======
@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = GasStationController)
>>>>>>> aabdae3b0a0cd5bd3c99868046f1de1245a7017e
public class GasStationControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    GasStationService gasStationService;

    Integer GS1id, GS2id;
    private String GS1carSharing;
    private GasStation GS1, GS2;

    private List<GasStation> GSList;

    @BeforeEach
    void setUp(){
        GS1 = new GasStation();
        GS1id = 1;
        GS1carSharing = "Enjoy";
        GS1.setDieselPrice(1.375);
        GS1.setGasPrice(1.753);
        GS1.setGasStationAddress("via Olanda, 12, Torino");
        GS1.setGasStationName("Esso");
        GS1.setHasDiesel(true);
        GS1.setHasGas(true);
        GS1.setHasMethane(false);
        GS1.setHasSuper(true);
        GS1.setHasSuperPlus(false);
        GS1.setMethanePrice(0);
        GS1.setReportDependability(0);
        GS1.setReportTimestamp(null);
        GS1.setReportUser(0);
        GS1.setSuperPrice(1.864);
        GS1.setSuperPlusPrice(0);
        GS1.setGasStationId(GS1id);
        GS1.setLat(45.048903);
        GS1.setLon(7.659812);
        GS1.setCarSharing(GS1carSharing);

        GS2 = new GasStation();
        GS2id = 2;
        GS1carSharing = "hdie";
        GS2.setDieselPrice(1.375);
        GS2.setGasPrice(1.753);
        GS2.setGasStationAddress("via Olanda, 12, Torino");
        GS2.setGasStationName("Esso");
        GS2.setHasDiesel(true);
        GS2.setHasGas(true);
        GS2.setHasMethane(false);
        GS2.setHasSuper(true);
        GS2.setHasSuperPlus(false);
        GS2.setMethanePrice(0);
        GS2.setReportDependability(0);
        GS2.setReportTimestamp(null);
        GS2.setReportUser(0);
        GS2.setSuperPrice(1.864);
        GS2.setSuperPlusPrice(0);
        GS2.setGasStationId(GS2id);
        GS2.setLat(45.048903);
        GS2.setLon(7.659812);
        GS2.setCarSharing(GS1carSharing);


        this.GSList.add(GS1);
        this.GSList.add(GS2);
    }





    @Test
    public void test_getGasStationById_existing() throws Exception {
        final int GSid;
        when(gasStationService.getGasStationById(GS1id)).thenReturn(GasStationConverter.convertEntityToDto(GS1));
        this.mockMvc.perform(get("api/getGasStation/{gasStationId}",GS1id))
        .andExpect(status().isOk());

    }

    /*@Test
    public void test_getGasStationById_notExisting() {

    }

    @Test
    public void test_saveGasStation(){

    }

    @Test
    public void test_getAllGasStations(){


    }

    @Test
    public void test_deleteGasStation_existing(){

     }

    @Test
    public void test_deleteGasStation_notExisting(){


    }

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
