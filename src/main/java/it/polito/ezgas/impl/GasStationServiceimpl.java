package it.polito.ezgas.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.polito.ezgas.Repository.GasStationRepository;
import it.polito.ezgas.Repository.UserRepository;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;
import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.service.GasStationService;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {
	@Autowired
	GasStationRepository GasStationRepository;
	@Autowired
	UserRepository userRepository;

	List<String> GasolineTypes = Arrays.asList("Diesel", "Super", "SuperPlus", "LPG", "Methane");

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) throws InvalidGasTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		// TODO Auto-generated method stub
		return null;
	}

	public double distance(double sLat, double sLon, double eLat, double eLon){
		double dLat = Math.toRadians((eLat - sLat));
		double dLon = Math.toRadians((eLon - sLon));

		double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(sLat) * Math.cos(eLat) * Math.pow(Math.sin(dLon/2),2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double r = 6371;

		return c * r;
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolineType,
			String carSharing) throws InvalidGasTypeException, GPSDataException {
		List<GasStation> gasStations = new ArrayList<>();

		if(lat > 90 || lat < -90 || lon > 180 || lon < -180)
			throw new GPSDataException("Invalid GPS Data");

		if(!GasolineTypes.contains(gasolineType) && gasolineType != null)
			throw new InvalidGasTypeException("Invalid Gasoline Type");

		if(carSharing != null)
			 gasStations = GasStationRepository.findByCarSharing(carSharing);
		else
			gasStations = GasStationRepository.findAll();

		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			double dist = distance(lat, lon, gs.getLat(), gs.getLon());
			if(dist <= 5)
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}

		return gasStationDtos;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolineType, String carSharing)
			throws InvalidGasTypeException {
		List<GasStation> gasStations;

		if(!GasolineTypes.contains(gasolineType) && gasolineType != null)
			throw new InvalidGasTypeException("Invalid Gasoline Type");

		if(carSharing != null)
			gasStations = GasStationRepository.findByCarSharing(carSharing);
		else
			gasStations = GasStationRepository.findAll();

		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			if((gasolineType == "Diesel" && gs.getHasDiesel()) ||
					(gasolineType == "Gasoline" && gs.getHasSuper()) ||
					(gasolineType == "PremiumGasoline" && gs.getHasSuperPlus()) ||
					(gasolineType == "LPG" && gs.getHasGas()) ||
					(gasolineType == "Methane" && gs.getHasMethane())){
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
			}
		}

		return gasStationDtos;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStation gasStation = GasStationRepository.findOne(gasStationId);
		if(gasStation == null)
			throw new InvalidGasStationException("Gas Station not found");

		if(dieselPrice < 0 || superPrice < 0 || superPlusPrice < 0 || gasPrice <0 || methanePrice < 0)
			throw new PriceException("Wrong Price");

		gasStation.setDieselPrice(dieselPrice);
		gasStation.setSuperPrice(superPrice);
		gasStation.setSuperPlusPrice(superPlusPrice);
		gasStation.setGasPrice(gasPrice);
		gasStation.setMethanePrice(methanePrice);

		User user = userRepository.findById(userId);
		if(user == null){
			throw new InvalidUserException("User not found");
		}
		gasStation.setReportUser(userId);
		
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		List<GasStation> gasStations = GasStationRepository.findByCarSharing(carSharing);
		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}

		return gasStationDtos;
	}

}
