package it.polito.ezgas;

import it.polito.ezgas.controller.ControllerTestSuite;
import it.polito.ezgas.converter.ConverterTestSuite;
import it.polito.ezgas.nodependencies.GetterSetterTests;
import it.polito.ezgas.repository.RepositoryTestSuite;
import it.polito.ezgas.service.ServiceTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EZGasApplicationTests.class,
        GetterSetterTests.class,
        ConverterTestSuite.class,
        ControllerTestSuite.class,
        RepositoryTestSuite.class,
        ServiceTestSuite.class
})
@SpringBootTest
public class AllTestsSuite {}

// this class is a quick way to launch all test suites at once