package it.polito.ezgas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.service.GasStationService;

import it.polito.ezgas.utils.Constants;


@RequestMapping("/gasstation")
@RestController

public class GasStationController {
	
	@Autowired
	GasStationService gasStationService;
	
	@RequestMapping(Constants.GET_GASSTATION_BY_ID)
	public GasStationDto getGasStationById(@PathVariable Integer gasStationId) {
		try {
			return gasStationService.getGasStationById(gasStationId);
		} catch (InvalidGasStationException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = Constants.GET_ALL_GASSTATIONS, method = RequestMethod.GET)
	public List<GasStationDto> getAllGasStations() {
		return gasStationService.getAllGasStations();
	}
	
	@RequestMapping(value = Constants.SAVE_GASSTATION, method = RequestMethod.POST)
	public void saveGasStation(@RequestBody GasStationDto gasStationDto) {
		try {
			gasStationService.saveGasStation(gasStationDto);
		} catch (PriceException | GPSDataException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = Constants.DELETE_GASSTATION, method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer gasStationId) {
		try {
			gasStationService.deleteGasStation(gasStationId);
		} catch (InvalidGasStationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}	
	
	
	@RequestMapping(value = Constants.GET_GASSTATIONS_BY_GASOLINETYPE, method = RequestMethod.GET)
	public List<GasStationDto> getGasStationsByGasolineType(@PathVariable String gasolinetype) {
		ArrayList<GasStationDto> tmp = null;
		
		try {
			tmp = (ArrayList<GasStationDto>) gasStationService.getGasStationsByGasolineType(gasolinetype);
		}
		catch (InvalidGasTypeException e) {
			
			System.out.println(e.getMessage());
			
			tmp = new ArrayList<GasStationDto>();
		}
		
		return tmp;
	}	
	
	@RequestMapping(value = Constants.GET_GASSTATIONS_BY_PROXIMITY, method = RequestMethod.GET)
	public List<GasStationDto> getGasStationsByProximity(@PathVariable Double myLat, @PathVariable Double myLon) {
		try {
			return gasStationService.getGasStationsByProximity(myLat, myLon);
		} catch (GPSDataException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return new ArrayList<GasStationDto>();
		}
	}	
	
	
	@RequestMapping(value = Constants.GET_GASSTATIONS_WITH_COORDINATES, method = RequestMethod.GET)
	public List<GasStationDto> getGasStationsWithCoordinates(@PathVariable Double myLat, @PathVariable Double myLon, @PathVariable String gasolineType, @PathVariable String carSharing) {
		
		ArrayList<GasStationDto> tmp = null;

		try {
			
			tmp = (ArrayList<GasStationDto>) gasStationService.getGasStationsWithCoordinates(myLat, myLon, gasolineType, carSharing);
			
		} catch (InvalidGasTypeException | GPSDataException e) {

			System.out.println(e.getMessage());
			
			tmp = new ArrayList<GasStationDto>();

		}
		
		return tmp;
	}
	
	
	@RequestMapping(value = Constants.SET_GASSTATION_REPORT, method = RequestMethod.POST)
	public void setGasStationReport(@PathVariable Integer gasStationId, @PathVariable double dieselPrice, @PathVariable double superPrice, @PathVariable double superPlusPrice, @PathVariable double gasPrice, @PathVariable double methanePrice, @PathVariable Integer userId) {

		try {
			gasStationService.setReport(gasStationId, dieselPrice, superPrice, superPlusPrice, gasPrice, methanePrice, userId);
		} catch (InvalidGasStationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (PriceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (InvalidUserException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
}


