package it.polito.ezgas.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


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
		Timestamp lastTS = Timestamp.valueOf(lastTimeStamp);
		Timestamp newTS = Timestamp.valueOf(newTimeStamp);
		long lastMS = lastTS.getTime();
		long newMS = newTS.getTime();
		long lastDay = TimeUnit.MILLISECONDS.toDays(lastMS);
		long newDay = TimeUnit.MILLISECONDS.toDays(newMS);

		// difference in ms converted in days
		difference = newDay - lastDay;
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
		GasStationDto gsDTo = null;
		
		if(gasStationDto.getLat() > 90 || gasStationDto.getLat() < -90 || gasStationDto.getLon() > 180 || gasStationDto.getLon() < -180) {
			throw new GPSDataException("Invalid GPS Data");
		}

		if(gasStationDto.getPriceReportDtos() == null){
			throw new PriceException("Wrong Exception");
		}

		String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		gasStationDto.setReportTimestamp(currentTimeStamp);

		if(gasStationDto.getGasStationId() != null) {
			GasStation gasStation = gasStationRepository.findById(gasStationDto.getGasStationId());

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

			gasStationRepository.save(gasStation);
			gsDTo = GasStationConverter.convertEntityToDto(gasStation);
		}
		else {
			GasStation gasStation = GasStationConverter.convertDtoToEntity(gasStationDto);
			gasStation.setReportTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			gasStationRepository.save(gasStation);
			gsDTo = GasStationConverter.convertEntityToDto(gasStation);
		}

		return gsDTo;
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

		if (lat > 90 || lat < -90 || lon > 180 || lon < -180) {
			throw new GPSDataException("Invalid GPS Data");
		}

		for(GasStation gs : gasStations) {
			if(distance(lat,lon,gs.getLat(),gs.getLon())<=1) {
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
		if (lat > 90 || lat < -90 || lon > 180 || lon < -180){
			throw new GPSDataException("Invalid GPS Data");
		}

		if (!GasolineTypes.contains(gasolineType) && gasolineType != null) {
			throw new InvalidGasTypeException("Invalid Gasoline Type");
		}

		List<GasStationDto> gasStationDtos = getGasStationsByGasolineType(gasolineType);
		List<GasStationDto> gsDtos = new ArrayList<>();

		for(GasStationDto gs : gasStationDtos){
			double dist = distance(lat, lon, gs.getLat(), gs.getLon());
			if(dist <= 1){
				if(carSharing.equals("null")){
					gsDtos.add(gs);
				} else if (gs.getCarSharing().equals(carSharing)){
					gsDtos.add(gs);
				}
			}
		}

		return gsDtos;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolineType, String carSharing)
			throws InvalidGasTypeException {
		if (!GasolineTypes.contains(gasolineType) && gasolineType != null)
			throw new InvalidGasTypeException("Invalid Gasoline Type");

		List<GasStationDto> gasStationDtos = getGasStationsByGasolineType(gasolineType);
		List<GasStationDto> gsDtos = new ArrayList<>();

		for(GasStationDto gs : gasStationDtos){
			if(carSharing.equals("null")){
				gsDtos.add(gs);
			} else if (gs.getCarSharing().equals(carSharing)){
				gsDtos.add(gs);
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

		if ((dieselPrice < 0 && gasStation.getHasDiesel()) ||
				(superPrice < 0 && gasStation.getHasSuper()) ||
				(superPlusPrice < 0 && gasStation.getHasSuperPlus()) ||
				(gasPrice < 0 && gasStation.getHasGas()) ||
				(methanePrice < 0 && gasStation.getHasMethane())) {
			throw new PriceException("Wrong Price");
		}

		if(gasStation.getHasDiesel()){
			gasStation.setDieselPrice(dieselPrice);
		}
		if(gasStation.getHasSuper()){
			gasStation.setSuperPrice(superPrice);
		}
		if(gasStation.getHasSuperPlus()){
			gasStation.setSuperPlusPrice(superPlusPrice);
		}
		if(gasStation.getHasGas()){
			gasStation.setGasPrice(gasPrice);
		}
		if(gasStation.getHasMethane()){
			gasStation.setMethanePrice(methanePrice);
		}

		User user = userRepository.findById(userId);
		if (user == null){
			throw new InvalidUserException("User not found");
		} else {
			gasStation.setReportUser(userId);
		}

		String oldTimeStamp = gasStation.getReportTimestamp();
		String newTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
		gasStation.setReportTimestamp(newTimeStamp);

		double repDependability = reportDependability(oldTimeStamp,newTimeStamp,user.getReputation());
		gasStation.setReportDependability(repDependability);

		gasStationRepository.save(gasStation);
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
