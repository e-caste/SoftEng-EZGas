package it.polito.ezgas.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GasStationControllerTests.class,
        UserControllerTests.class
})
@SpringBootTest
public class ControllerTestSuite {}
