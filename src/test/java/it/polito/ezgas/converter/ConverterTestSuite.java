package it.polito.ezgas.converter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GasStationConverterTests.class,
        UserConverterTests.class
})
@SpringBootTest
public class ConverterTestSuite {}
