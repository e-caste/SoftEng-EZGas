package it.polito.ezgas.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.concurrent.TimeUnit;


import exception.*;
import it.polito.ezgas.repository.GasStationRepository;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.converter.GasStationConverter;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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


	List<String> GasolineTypes = Arrays.asList("Diesel", "Super", "SuperPlus", "LPG", "Methane", "PremiumDiesel");

	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		GasStation gasStation = gasStationRepository.findById(gasStationId);

		if (gasStation == null) {
			throw new InvalidGasStationException("GasStation not found");
		}
		return GasStationConverter.convertEntityToDto(gasStation);
	}

	public double reportDependability(String lastTimeStamp, String newTimeStamp, int userTrustLevel){
		double dependability=0;
		double difference;
		double obsolescence;

		DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		LocalDate lastDate = LocalDate.parse(lastTimeStamp, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		LocalDate newDate = LocalDate.parse(newTimeStamp, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		Timestamp lastTS = Timestamp.valueOf(lastDate.atTime(LocalTime.MIDNIGHT));
		Timestamp newTS = Timestamp.valueOf(newDate.atTime(LocalTime.MIDNIGHT));
		// values in milliseconds
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

		if(gasStationDto.getLat() > 90 || gasStationDto.getLat() <= -90 || gasStationDto.getLon() > 180 || gasStationDto.getLon() <= -180) {
			throw new GPSDataException("Invalid GPS Data");
		}

		String currentTimeStamp = new SimpleDateFormat("MM-dd-YYYY").format(new Date(System.currentTimeMillis()));
		gasStationDto.setReportTimestamp(currentTimeStamp);

		GasStation gasStation = gasStationRepository.findById(gasStationDto.getGasStationId());

		if(gasStation != null) {
			gasStation.setGasStationId(gasStationDto.getGasStationId());
			gasStation.setGasStationName(gasStationDto.getGasStationName());
			gasStation.setGasStationAddress(gasStationDto.getGasStationAddress());
			gasStation.setHasDiesel(gasStationDto.getHasDiesel());
			gasStation.setHasSuper(gasStationDto.getHasSuper());
			gasStation.setHasSuperPlus(gasStationDto.getHasSuperPlus());
			gasStation.setHasGas(gasStationDto.getHasGas());
			gasStation.setHasMethane(gasStationDto.getHasMethane());
			gasStation.setHasPremiumDiesel(gasStationDto.getHasPremiumDiesel());
			if(gasStationDto.getCarSharing().equals("null")){
				gasStation.setCarSharing(null);
			}
			gasStation.setLat(gasStationDto.getLat());
			gasStation.setLon(gasStationDto.getLon());

			if (gasStationDto.getHasDiesel()) {
				if (gasStationDto.getDieselPrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setDieselPrice(gasStationDto.getDieselPrice());
			}

			if (gasStationDto.getHasSuper()) {
				if (gasStationDto.getSuperPrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setSuperPrice(gasStationDto.getSuperPrice());
			}

			if (gasStationDto.getHasSuperPlus()) {
				if (gasStationDto.getSuperPlusPrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setSuperPlusPrice(gasStationDto.getSuperPlusPrice());
			}

			if (gasStationDto.getHasGas()) {
				if (gasStationDto.getGasPrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setGasPrice(gasStationDto.getGasPrice());
			}

			if (gasStationDto.getHasMethane()) {
				if (gasStationDto.getMethanePrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setMethanePrice(gasStationDto.getMethanePrice());
			}
			if(gasStationDto.getHasPremiumDiesel()){
				if (gasStationDto.getPremiumDieselPrice()<0){
					throw new PriceException("Invalid price");
				}
				gasStation.setPremiumDieselPrice(gasStationDto.getPremiumDieselPrice());
			}

			gasStationRepository.save(gasStation);
			gsDTo = GasStationConverter.convertEntityToDto(gasStation);
		}
		else {
			gasStation = GasStationConverter.convertDtoToEntity(gasStationDto);
			if(gasStation.getCarSharing().equals("null")){
				gasStation.setCarSharing(null);
			}

			gasStation.setReportTimestamp(new SimpleDateFormat("MM-dd-YYYY").format(new Date(System.currentTimeMillis())));
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
		try {
			gasStationRepository.delete(gasStationId);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
		return true;
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
				case "PremiumDiesel":
					if (gs.getHasPremiumDiesel()) {
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

	@Override
	public List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius) throws GPSDataException {
		List<GasStation> gasStations = gasStationRepository.findAll();
		List<GasStationDto> gasStationDtos = new ArrayList<>();

		if (lat > 90 || lat < -90 || lon > 180 || lon < -180) {
			throw new GPSDataException("Invalid GPS Data");
		}

		if(radius <= 0){
			radius = 1;
		}

		for(GasStation gs : gasStations) {
			if(distance(lat,lon,gs.getLat(),gs.getLon())<=radius) {
				gasStationDtos.add(GasStationConverter.convertEntityToDto(gs));
			}
		}
		return gasStationDtos;
	}

	//Ref: https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
	public double distance(double sLat, double sLon, double eLat, double eLon){
		double r = 6371;
		double dLat = Math.toRadians((eLat - sLat));
		double dLon = Math.toRadians((eLon - sLon));

		sLat = Math.toRadians(sLat);
		eLat = Math.toRadians(eLat);

		double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(eLat) * Math.cos(sLat) * Math.pow(Math.sin(dLon/2),2);
		double c = 2 * Math.asin(Math.sqrt(a));

		double result = c * r;

		return result;
	}


	@Override
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, String gasolinetype, String carsharing) throws InvalidGasTypeException, GPSDataException, InvalidCarSharingException{
		if (lat > 90 || lat < -90 || lon > 180 || lon < -180){
			throw new GPSDataException("Invalid GPS Data");
		}

		if(!carsharing.equals("Enjoy") && !carsharing.equals("Car2Go") && carsharing != null){
			throw new InvalidCarSharingException("Invalid CarSharing Type");
		}

		if (!GasolineTypes.contains(gasolinetype) && gasolinetype != null) {
			throw new InvalidGasTypeException("Invalid Gasoline Type");
		}

		List<GasStationDto> gasStationDtos = getGasStationsByGasolineType(gasolinetype);
		List<GasStationDto> gsDtos = new ArrayList<>();

		if(radius<=0){
			radius = 1;
		}

		for(GasStationDto gs : gasStationDtos){
			double dist = distance(lat, lon, gs.getLat(), gs.getLon());
			if(dist <= radius){
				if(carsharing.equals("null")){
					gsDtos.add(gs);
				} else if (gs.getCarSharing().equals(carsharing)){
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

		return gsDtos;
	}

	@Override
	public void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId) throws InvalidGasStationException, PriceException, InvalidUserException {
		GasStation gasStation = gasStationRepository.findById(gasStationId);

		if (gasStation == null)
			throw new InvalidGasStationException("Gas Station not found");

		if(gasStation.getHasDiesel()){
			if(dieselPrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		if(gasStation.getHasSuper()){
			if(superPrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		if(gasStation.getHasSuperPlus()){
			if(superPlusPrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		if(gasStation.getHasGas()){
			if(gasPrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		if(gasStation.getHasMethane()){
			if(methanePrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		if(gasStation.getHasPremiumDiesel()){
			if(premiumDieselPrice < 0){
				throw new PriceException("Wrong Price");
			}
		}

		User user = userRepository.findById(userId);
		if (user == null)
			throw new InvalidUserException("User not found");

		String oldTimeStamp = gasStation.getReportTimestamp();
		String newTimeStamp = new SimpleDateFormat("MM-dd-yyyy").format(new Date(System.currentTimeMillis()));
		LocalDate lastDate = LocalDate.parse(oldTimeStamp,DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		LocalDate newDate = LocalDate.parse(newTimeStamp,DateTimeFormatter.ofPattern("MM-dd-yyyy"));
		long lastDay = TimeUnit.MILLISECONDS.toDays(Timestamp.valueOf(lastDate.atTime(LocalTime.MIDNIGHT)).getTime());
		long toDay = TimeUnit.MILLISECONDS.toDays(Timestamp.valueOf(newDate.atTime(LocalTime.MIDNIGHT)).getTime());

		if((gasStation.getUser() == null) ||
				(user.getReputation() >= gasStation.getUser().getReputation()) ||
				((user.getReputation() < gasStation.getUser().getReputation()) && (toDay-lastDay)>4)){
			gasStation.setReportTimestamp(newTimeStamp);
			gasStation.setReportUser(userId);
			gasStation.setUser(user);

			double repDependability = reportDependability(oldTimeStamp, newTimeStamp, user.getReputation());
			gasStation.setReportDependability(repDependability);

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
			if(gasStation.getHasPremiumDiesel()){
				gasStation.setPremiumDieselPrice(premiumDieselPrice);
			}
		}

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
