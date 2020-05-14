package it.polito.ezgas.impl;

import java.util.ArrayList;
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

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolineType,
			String carSharing) throws InvalidGasTypeException, GPSDataException {
		List<GasStation> gasStations = GasStationRepository.findByCoordinates(lat, lon);

		if(gasStations == null)
			throw new GPSDataException("Gas Station not found (GPSException)");
		for(GasStation gs : gasStations){
			if(gasolineType == "Diesel" && !gs.getHasDiesel())
				gasStations.remove(gs);
			if(gasolineType == "Gasoline" && !gs.getHasSuper())
				gasStations.remove(gs);
			if(gasolineType == "PremiumGasoline" && !gs.getHasSuperPlus())
				gasStations.remove(gs);
			if(gasolineType == "LPG" && !gs.getHasGas())
				gasStations.remove(gs);
			if(gasolineType == "Methane" && !gs.getHasMethane())
				gasStations.remove(gs);
		}
		if(gasStations == null)
			throw new InvalidGasTypeException("Gas Station not found (GasolineTypeException)");

		for(GasStation gs : gasStations){
			if(gs.getCarSharing() != carSharing)
				gasStations.remove(gs);
		}

		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}

		return gasStationDtos;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolineType, String carSharing)
			throws InvalidGasTypeException {
		List<GasStation> gasStations = GasStationRepository.findAll();

		for(GasStation gs : gasStations){
			if(gasolineType == "Diesel" && !gs.getHasDiesel())
				gasStations.remove(gs);
			if(gasolineType == "Gasoline" && !gs.getHasSuper())
				gasStations.remove(gs);
			if(gasolineType == "PremiumGasoline" && !gs.getHasSuperPlus())
				gasStations.remove(gs);
			if(gasolineType == "LPG" && !gs.getHasGas())
				gasStations.remove(gs);
			if(gasolineType == "Methane" && !gs.getHasMethane())
				gasStations.remove(gs);
		}
		if(gasStations == null)
			throw new InvalidGasTypeException("Gas Station not found (GasolineTypeException)");

		for(GasStation gs : gasStations){
			if(gs.getCarSharing() != carSharing)
				gasStations.remove(gs);
		}

		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
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

		if(gasStation.getHasDiesel())
			gasStation.setDieselPrice(dieselPrice);
		else
			throw new PriceException("Gas Station does not provide Diesel");

		if(gasStation.getHasSuper())
			gasStation.setSuperPrice(superPrice);
		else
			throw new PriceException("Gas Station does not provide Super");

		if(gasStation.getHasSuperPlus())
			gasStation.setSuperPlusPrice(superPlusPrice);
		else
			throw new PriceException("Gas Station does not provide SuperPlus");

		if(gasStation.getHasGas())
			gasStation.setGasPrice(gasPrice);
		else
			throw new PriceException("Gas Station does not provide Gas");

		if(gasStation.getHasMethane())
			gasStation.setMethanePrice(methanePrice);
		else
			throw new PriceException("Gas Station does not provide Methane");

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
