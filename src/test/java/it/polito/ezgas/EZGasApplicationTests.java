package it.polito.ezgas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
