package it.polito.ezgas.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GasStationRepositoryTests.class,
        UserRepositoryTests.class
})
@SpringBootTest
public class RepositoryTestSuite {}
