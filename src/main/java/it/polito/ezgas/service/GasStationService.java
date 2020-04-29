package it.polito.ezgas.service;

import java.util.List;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto; 

/** 
 *
 * Created by softeng on 27/4/2020.
 */
public interface GasStationService {
	
	
	/*
	 * Queries the database and return a single gas station corresponding to the database given as parameter
	 * Returns null if no gas station is found with the given id
	 */
    GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException;
    
    /*
     * Receives a GasStationDto and store it in the database
     * Throws exceptions in case of negative prices or wrong latitude and longitude values in the GasStationDto
     */
    GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException;
    
    /*
     * Returns an ArrayList with all the GasStations stored in the database
     * Returns an empty ArrayList if no gas station is registered in the database
     */
    List<GasStationDto> getAllGasStations();
    
    /*
     * Deletes from the database the GasStation with the id passed as parameter. Throws an exception in case of invalid id (<0).
     * Returns null in case of not found gas station
     */
    Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException;
    
    /*
     * Returns all gas stations that provide the gasoline type provided as parameter, sorted by increasing price of that gasoline type.
     * Returns an empty ArrayList if no gas station in the database provides the given gasoline type
     * Throws an exception if an invalid gasoline type is given as parameter
     */
	List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException;
	
    /*
     * Returns all gas stations within 1km from the GeoPoint whose latitude and longitude are passed as parameters.
     * Returns an empty ArrayList if no gas station in the database is located within 1km from that geopoint
     * Throws an exception if an invalid value is given for latitude and/or longitude
     */
	List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException;
	
    /*
     * Returns all gas stations within 1km from the GeoPoint whose latitude and longitude are passed as parameters.
     * It receives as parameters a gasolinetype and a carsharing value. 
	 * If gasolinetype is different than "null" (string), it filters the list of gas stations keeping only those providing such gasoline type
	 * If carsharing is different than "null" (string), it filters the list of gas stations keeping only those affiliated to that carsharing company
     * Returns an empty ArrayList if no gas station is found in the database with the given parameters
     * Throws an exception if an invalid value is given for latitude and/or longitude, gasolinetype or carsharing string parameters
     */
	List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype, String carsharing) throws InvalidGasTypeException, GPSDataException;
	
	
    /*
     * Returns all gas stations within 1km from the GeoPoint whose latitude and longitude are passed as parameters.
     * Returns an empty ArrayList if no gas station in the database is located within 1km from that geopoint
     * Throws an exception if an invalid value is given for latitude and/or longitude
     */
	List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing) throws InvalidGasTypeException;
	
	/*
	 * Adds a report to a gasStation, receving as parameters the prices for all the fuels and the user ID of the reporting user
	 * It throws an exception if a negative value for a fuel is given for a type of fuel provided by the gas station
	 */
	void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, Integer userId) throws InvalidGasStationException, PriceException, InvalidUserException;
	
	
	/*
     * Returns all gas stations within 1km from the GeoPoint whose latitude and longitude are passed as parameters.
     * Returns an empty ArrayList if no gas station in the database is located within 1km from that geopoint
     * Throws an exception if an invalid value is given for latitude and/or longitude
	 */
	List<GasStationDto> getGasStationByCarSharing(String carSharing);

}
