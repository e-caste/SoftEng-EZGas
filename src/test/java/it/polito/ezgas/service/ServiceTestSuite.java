package it.polito.ezgas.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GasStationServiceimplTests.class,
        UserServiceimplTests.class
})
@SpringBootTest
public class ServiceTestSuite {}
