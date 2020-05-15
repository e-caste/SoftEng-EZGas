package it.polito.ezgas.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.converter.GasStationConverter;
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
	GasStationRepository gasStationRepository;
	@Autowired
	UserRepository userRepository;

	List<String> GasolineTypes = Arrays.asList("Diesel", "Super", "SuperPlus", "LPG", "Methane");

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		GasStation gasStation = gasStationRepository.findById(gasStationId);
		if (gasStation == null) {
			throw new InvalidGasStationException("GasStation not found");
		}
		return GasStationConverter.convertEntityToDto(gasStation);
	}
	
	public double reportDependability(String lastTimeStamp, String newTimeStamp, int userTrustLevel) {
		double dependability;
		double difference;
		double obsolescence;
		
		// values in milliseconds
		long lastTS = Timestamp.valueOf(lastTimeStamp).getTime();
		long newTS = Timestamp.valueOf(newTimeStamp).getTime();

		// difference in ms converted in days
		difference = (newTS - lastTS) / (7*24*60*60*1000);
		if (difference > 7) {
			obsolescence = 0;
		}
		else {
			obsolescence = 1 - (difference / 7);
		}
		
		dependability = 50 * (userTrustLevel + 5) / 10 + 50 * obsolescence;
		
		return dependability;
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
		GasStation gasStation = gasStationRepository.findById(gasStationDto.getGasStationId());
		
		if(gasStationDto.getLat() > 90 || gasStationDto.getLat() < -90 || gasStationDto.getLon() > 180 || gasStationDto.getLon() < -180) {
			throw new GPSDataException("Invalid GPS Data");
		}	
		
		User user = userRepository.findById(gasStationDto.getReportUser()); 
		String currentTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		gasStationDto.setReportTimestamp(currentTimeStamp);
		
	
		if(gasStation != null) {					
			
			gasStation.setGasStationId(gasStationDto.getGasStationId());
			gasStation.setGasStationName(gasStationDto.getGasStationName());
			gasStation.setGasStationAddress(gasStationDto.getGasStationAddress());
			gasStation.setHasDiesel(gasStationDto.getHasDiesel());
			gasStation.setHasSuper(gasStationDto.getHasSuper());
			gasStation.setHasSuperPlus(gasStationDto.getHasSuperPlus());
			gasStation.setHasGas(gasStationDto.getHasGas());
			gasStation.setHasMethane(gasStationDto.getHasMethane());
			gasStation.setCarSharing(gasStationDto.getCarSharing());
			gasStation.setLat(gasStationDto.getLat());
			gasStation.setLon(gasStationDto.getLon());
			
			if (gasStationDto.getHasDiesel()) {
				if (gasStationDto.getDieselPrice() < 0) {
					throw new PriceException("Wrong price"); 
				}
				
				gasStation.setDieselPrice(gasStationDto.getDieselPrice());
			}
			
			if (gasStationDto.getHasSuper()) {
				if (gasStationDto.getSuperPrice() < 0) {
					throw new PriceException("Wrong price"); 
				}
				gasStation.setSuperPrice(gasStationDto.getSuperPrice());
			}
			
			if (gasStationDto.getHasSuper()) {
				if (gasStationDto.getSuperPrice() < 0) {
					throw new PriceException("Wrong price"); 
				}
				gasStation.setSuperPlusPrice(gasStationDto.getSuperPlusPrice());
			}
			
			if (gasStationDto.getHasGas()) {
				if (gasStationDto.getGasPrice() < 0) {
					throw new PriceException("Wrong price"); 
				}
				gasStation.setGasPrice(gasStationDto.getGasPrice());
			}
			
			if (gasStationDto.getHasMethane()) {
				if (gasStationDto.getMethanePrice() < 0) {
					throw new PriceException("Wrong price"); 
				}
				gasStation.setMethanePrice(gasStationDto.getMethanePrice());
			}

			double repDependability = reportDependability(gasStation.getReportTimestamp(),gasStationDto.getReportTimestamp(),user.getReputation());
			gasStationDto.setReportDependability(repDependability);
			
			gasStation.setReportUser(gasStationDto.getReportUser());
			gasStation.setReportTimestamp(gasStationDto.getReportTimestamp());
			gasStation.setReportDependability(gasStationDto.getReportDependability());
		}
		else {
			
			double repDependability = reportDependability(gasStationDto.getReportTimestamp(),gasStationDto.getReportTimestamp(),user.getReputation());
			gasStationDto.setReportDependability(repDependability);
			
			gasStation = GasStationConverter.convertDtoToEntity(gasStationDto);
			
		}
		gasStationRepository.save(gasStation);
		return GasStationConverter.convertEntityToDto(gasStation);
	}

	@Override
	public List<GasStationDto> getAllGasStations() {
		List<GasStation> gasStations = gasStationRepository.findAll();
		List<GasStationDto> gasStationDtos = new ArrayList<>();
		for(GasStation gs : gasStations) {
			gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}
		return gasStationDtos;
	}

	@Override
	public Boolean deleteGasStation(Integer gasStationId) throws InvalidGasStationException {
		GasStation gasStation = gasStationRepository.findById(gasStationId);
		if(gasStation == null) {
			throw new InvalidGasStationException("GasStation not found");
		}
		gasStationRepository.delete(gasStationId);
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsByGasolineType(String gasolineType) throws InvalidGasTypeException {
		List<GasStation> gasStations = gasStationRepository.findAll();
		List<GasStationDto> gasStationsDto = new ArrayList<>();
		if(!GasolineTypes.contains(gasolineType)) {
			throw new InvalidGasTypeException("Gasoline Type not found");
		}
		for(GasStation gs : gasStations) {
			switch (gasolineType) {
				case "Diesel":
					if (gs.getHasDiesel()) {
						gasStationsDto.add(GasStationConverter.convertEntityToDto(gs));
					}
				break;
				case "Methane":
					if (gs.getHasMethane()) {
						gasStationsDto.add(GasStationConverter.convertEntityToDto(gs));
					}
				break;
				case "Super":
					if (gs.getHasSuper()) {
						gasStationsDto.add(GasStationConverter.convertEntityToDto(gs));
					}
				break;
				case "SuperPlus":
					if (gs.getHasSuperPlus()) {
						gasStationsDto.add(GasStationConverter.convertEntityToDto(gs));
					}
				break;
				case "LPG":
					if (gs.getHasGas()) {
						gasStationsDto.add(GasStationConverter.convertEntityToDto(gs));
					}
				break;
			}
		}
		return gasStationsDto;
	}

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon) throws GPSDataException {
		List<GasStation> gasStations = gasStationRepository.findAll();
		List<GasStationDto> gasStationDtos = new ArrayList<>();
		for(GasStation gs : gasStations) {
			if(distance(lat,lon,gs.getLat(),gs.getLon())<=5) {
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
			}
		}
		return gasStationDtos;
	}

	public double distance(double sLat, double sLon, double eLat, double eLon){
		double dLat = Math.toRadians((eLat - sLat));
		double dLon = Math.toRadians((eLon - sLon));

		// TODO: rename these variables with descriptive names
		double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(sLat) * Math.cos(eLat) * Math.pow(Math.sin(dLon/2),2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double r = 6371;

		return c * r;
	}

	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolineType,
			String carSharing) throws InvalidGasTypeException, GPSDataException {
		List<GasStation> gasStations = new ArrayList<>();

		if (lat > 90 || lat < -90 || lon > 180 || lon < -180)
			throw new GPSDataException("Invalid GPS Data");

		if (!GasolineTypes.contains(gasolineType) && gasolineType != null)
			throw new InvalidGasTypeException("Invalid Gasoline Type");

		if (carSharing != null)
			 gasStations = gasStationRepository.findByCarSharing(carSharing);
		else
			gasStations = gasStationRepository.findAll();

		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for(GasStation gs : gasStations){
			double dist = distance(lat, lon, gs.getLat(), gs.getLon());
			if (dist <= 5)
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}

		return gasStationDtos;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolineType, String carSharing)
			throws InvalidGasTypeException {
		List<GasStation> gasStations;

		if (!GasolineTypes.contains(gasolineType) && gasolineType != null)
			throw new InvalidGasTypeException("Invalid Gasoline Type");

		if (carSharing != null)
			gasStations = gasStationRepository.findByCarSharing(carSharing);
		else
			gasStations = gasStationRepository.findAll();

		List<GasStationDto> gasStationDtos = new ArrayList<>();
		

		for(GasStation gs : gasStations){
			if ((gasolineType.equals("Diesel") && gs.getHasDiesel()) ||
					(gasolineType.equals("Gasoline") && gs.getHasSuper()) ||
					(gasolineType.equals("PremiumGasoline") && gs.getHasSuperPlus()) ||
					(gasolineType.equals("LPG") && gs.getHasGas()) ||
					(gasolineType.equals("Methane") && gs.getHasMethane())){
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
			}
		}

		return gasStationDtos;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStation gasStation = gasStationRepository.findOne(gasStationId);
		if (gasStation == null)
			throw new InvalidGasStationException("Gas Station not found");

		if (dieselPrice < 0 || superPrice < 0 || superPlusPrice < 0 || gasPrice <0 || methanePrice < 0)
			throw new PriceException("Wrong Price");

		gasStation.setDieselPrice(dieselPrice);
		gasStation.setSuperPrice(superPrice);
		gasStation.setSuperPlusPrice(superPlusPrice);
		gasStation.setGasPrice(gasPrice);
		gasStation.setMethanePrice(methanePrice);

		User user = userRepository.findById(userId);
		if (user == null){
			throw new InvalidUserException("User not found");
		}
		gasStation.setReportUser(userId);
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		List<GasStation> gasStations = gasStationRepository.findByCarSharing(carSharing);
		List<GasStationDto> gasStationDtos = new ArrayList<>();

		for (GasStation gs : gasStations){
			gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
		}

		return gasStationDtos;
	}
}
