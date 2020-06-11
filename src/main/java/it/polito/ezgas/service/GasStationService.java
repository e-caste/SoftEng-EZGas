package it.polito.ezgas.service;

import java.util.List;

import exception.*;
import it.polito.ezgas.dto.GasStationDto;

/** 
 *
 * Created by softeng on 27/4/2020.
 *
 * Update V2 :
 * 1) issue 1 		--->		Premium diesel has been added to the list of selectable fuel types in the front-end.
 * 								Back-end side GasStationDto, GasStation, GasStationRepository and the APIs have been
 * 								updated coherently.
 * 2) issue 6 		--->		Update of the APIs "getGasStationsByProximity" and "getGasStationsWithCoordinates" to
 * 								support the customization of the search radius. The parameter "myRadius" is thought to be
 * 								considered as an INTEGER number of kilometers. If it is set to an invalid value (zero or
 * 								negative) it should be ignored and the default value (1km should be considered).
 * 3) issue 16/CR7  --->		The lack of a gasStation fuel price now is equivalent to null and not to -1. Dtos
 * 								don't use primitive double type anymore, they use Double class. GasStationDto and
 * 								GasStation entity have been updated coherently.
 * 							    PS To achieve a "softer" migration from V1 to V2 the "setReport" API has almost the same
 * 							    prototype even though the controller is receiving a new Dto for the price report.
 * 4) issue 22/23	--->		InvalidCarSharingException has been added to support a better error handling.
 * 								If the GasStationDto (sent as parameter of saveGasStation) has a non existing or an
 * 								invalid id this should be seen as a registration of a new GasStation, thus no
 * 								InvalidGasStationException should be thrown.
 *								DeleteGasStation throws an exception if an invalid (negative) id is used as parameter.
 *								If instead a non existing id is used as parameter the API should return false.
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
     * Returns false in case of not found gas station
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

	/********************************************************************************/
	/*
	 * Returns all gas stations within the radius from the GeoPoint whose latitude and longitude are passed as parameters.
	 * If radius is equals to zero or it is a negative integer the default value (1Km) will be considered.
	 * Returns an empty ArrayList if no gas station in the database is located within 1km from that geopoint
	 * Throws an exception if an invalid value is given for latitude and/or longitude
	 */
	List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius) throws GPSDataException;

    /*
     * Returns all gas stations within 1km from the GeoPoint whose latitude and longitude are passed as parameters.
     * It receives as parameters a gasolinetype and a carsharing value. 
	 * If gasolinetype is different than "null" (string), it filters the list of gas stations keeping only those providing such gasoline type
	 * If carsharing is different than "null" (string), it filters the list of gas stations keeping only those affiliated to that carsharing company
     * Returns an empty ArrayList if no gas station is found in the database with the given parameters
     * Throws an exception if an invalid value is given for latitude and/or longitude, gasolinetype or carsharing string parameters
     */
	//List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype, String carsharing) throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException;
	/********************************************************************************/
	/*
	 * Returns all gas stations within the radius from the GeoPoint whose latitude and longitude are passed as parameters.
	 * If radius is equals to zero or it is a negative integer the default value (1Km) will be considered.
	 * If gasolinetype is different than "null" (string), it filters the list of gas stations keeping only those providing such gasoline type
	 * If carsharing is different than "null" (string), it filters the list of gas stations keeping only those affiliated to that carsharing company
	 * Returns an empty ArrayList if no gas station is found in the database with the given parameters
	 * Throws an exception if an invalid value is given for latitude and/or longitude, gasolinetype or carsharing string parameters
	 */

	List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, String gasolinetype, String carsharing) throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException;

	/*
	 * If gasolinetype is different than "null" (string), it filters the list of gas stations keeping only those providing such gasoline type
	 * If carsharing is different than "null" (string), it filters the list of gas stations keeping only those affiliated to that carsharing company
     * Returns an empty ArrayList if no gas station is found in the database with the given parameters
     * Throws an exception if an invalid value is given for latitude and/or longitude, gasolinetype or carsharing string parameters
     */
	List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing) throws InvalidGasTypeException, InvalidCarSharingException;
	
	/*
	 * Adds a report to a gasStation, receving as parameters the prices for all the fuels and the user ID of the reporting user
	 * It throws an exception if a negative value for a fuel is given for a type of fuel provided by the gas station
	 */
	void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId) throws InvalidGasStationException, PriceException, InvalidUserException;


	/*
     * Returns all gas stations that provide the car sharing service passed as parameter, sorted by alphabetical order.
     * Returns an empty ArrayList if no gas station in the database provides such car sharing service
	 */
	List<GasStationDto> getGasStationByCarSharing(String carSharing);

}
