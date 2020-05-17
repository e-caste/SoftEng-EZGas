/*package it.polito.ezgas;

import java.sql.Timestamp;

import org.junit.Test;
import static org.junit.Assert.*;

import it.polito.ezgas.impl.GasStationServiceimpl;

public class GasStationServiceimplTests {
	
	
	
	
	//	Dependability = 50 * (userTrustLevel +5)/10 + 50 * obsolescence 
	//	if (newTimeStamp - lastTimeStamp) > 7 days? obsolescence = 0 : obsolescence = 1 - (today - P.time_tag)/7;
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent() {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 35;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_notObsolescent() {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 20, 40, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 77.857;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_perfectUser() {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 19, 22, 32, 0);
		int userTrustLevel = 5;
		double expectedRes = 100;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_worstUser() {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 21, 30, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 50;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent_worstUser() {
		GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 0;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
}*/