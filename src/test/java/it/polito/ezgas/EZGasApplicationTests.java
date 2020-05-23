package it.polito.ezgas;

import it.polito.ezgas.converter.GasStationConverterTests;
import it.polito.ezgas.converter.UserConverterTests;
import it.polito.ezgas.nodependencies.GetterSetterTests;
import it.polito.ezgas.service.GasStationServiceimplTests;
import it.polito.ezgas.service.UserServiceimplTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
@RunWith(Suite.class)
@Suite.SuiteClasses({
		GasStationConverterTests.class,
		GasStationServiceimplTests.class,
		GetterSetterTests.class,
		UserConverterTests.class,
		UserServiceimplTests.class
})
@SpringBootTest
public class EZGasApplicationTests {

	@Test
	public void contextLoads() {
	}
}
