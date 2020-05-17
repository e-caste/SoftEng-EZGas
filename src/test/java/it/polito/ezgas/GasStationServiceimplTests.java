package it.polito.ezgas;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import exception.InvalidGasTypeException;

import static org.junit.Assert.*;

import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.impl.GasStationServiceimpl;
import it.polito.ezgas.service.GasStationService;

public class GasStationServiceimplTests {
	private GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();

	@Test 
	public void test_distance_nearest(){
		//retitalia c.so Duca Abruzzi 45.064666, 7.664134
		//poliTo 45.062453, 7.662429
		//~0.3km 0.2mi
		assertEquals(0.3, gasStationServiceimpl.distance(45.064666, 7.664134, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_near(){
		//repsol c.so lepanto 45.047977, 7.660156
		//poliTo 45.062453, 7.662429
		//~1.6km 1mi
		assertEquals(1.6, gasStationServiceimpl.distance(45.047977, 7.660156, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_far(){
		//punto benzina c.so Casale 45.070408, 7.727038
		//poliTo 45.062453, 7.662429
		//~5.1km 3.1mi
		assertEquals(5.1, gasStationServiceimpl.distance(45.070408, 7.727038, 45.062453, 7.662429), 0.2);
	}
	
	@Test
	public void test_distance_furthest(){
		//Gas Station s.r.l. 45.072812, 7.573000
		//poliTo 45.062453, 7.662429
		//~7.1km 4.4mi
		assertEquals(7.1, gasStationServiceimpl.distance(45.072812, 7.573000, 45.062453, 7.662429), 0.2);
	}
	
	//	Dependability = 50 * (userTrustLevel +5)/10 + 50 * obsolescence 
	//	if (newTimeStamp - lastTimeStamp) > 7 days? obsolescence = 0 : obsolescence = 1 - (today - P.time_tag)/7;
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 35;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_notObsolescent() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 20, 40, 32, 0);
		int userTrustLevel = 2;
		double expectedRes = 77.857;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_perfectUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 19, 22, 32, 0);
		int userTrustLevel = 5;
		double expectedRes = 100;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_sameDay_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 12, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 12, 21, 30, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 50;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void test_reportDependability_obsolescent_worstUser() {
		//GasStationServiceimpl gasStationServiceimpl = new GasStationServiceimpl();
		Timestamp lastTimeStamp = new Timestamp(2019, 12, 11, 18, 20, 32, 0);
		Timestamp newTimeStamp = new Timestamp(2019, 12, 19, 22, 10, 32, 0);
		int userTrustLevel = -5;
		double expectedRes = 0;
		
		assertEquals(expectedRes, gasStationServiceimpl.reportDependability(lastTimeStamp.toString(), newTimeStamp.toString(), userTrustLevel), 0.01);
	}
	
}