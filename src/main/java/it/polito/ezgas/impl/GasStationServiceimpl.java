package it.polito.ezgas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.GPSDataException;
import exception.InvalidGasStationException;
import exception.InvalidGasTypeException;
import exception.InvalidUserException;
import exception.PriceException;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.service.GasStationService;
import it.polito.ezgas.Repository.GasStationRepository;
import it.polito.ezgas.converter.GasStationConverter;

/**
 * Created by softeng on 27/4/2020.
 */
@Service
public class GasStationServiceimpl implements GasStationService {

	@Autowired
	GasStationRepository gasStationRepository;
	
	@Override
	public GasStationDto getGasStationById(Integer gasStationId) throws InvalidGasStationException {
		GasStation gasStation = gasStationRepository.findById(gasStationId);
		return GasStationConverter.convertEntityToDto(gasStation);
	}

	@Override
	public GasStationDto saveGasStation(GasStationDto gasStationDto) throws PriceException, GPSDataException {
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
			gasStation.setCarSharing(gasStationDto.getCarSharing());
			gasStation.setLat(gasStationDto.getLat());
			gasStation.setLon(gasStationDto.getLon());
			
			if(gasStation.getHasDiesel()) {
				gasStation.setDieselPrice(gasStationDto.getDieselPrice());
			}
			
			if(gasStation.getHasSuper()) {
				gasStation.setSuperPrice(gasStationDto.getSuperPrice());
			}
			
			if(gasStation.getHasSuperPlus()) {
				gasStation.setSuperPlusPrice(gasStationDto.getSuperPlusPrice());
			}
			
			if(gasStation.getHasGas()) {
				gasStation.setGasPrice(gasStationDto.getGasPrice());
			}
			
			if(gasStation.getHasMethane()) {
				gasStation.setMethanePrice(gasStationDto.getMethanePrice());
			}
			
			gasStation.setReportUser(gasStationDto.getReportUser());
			gasStation.setReportTimestamp(gasStationDto.getReportTimestamp());
			gasStation.setReportDependability(gasStationDto.getReportDependability());
			
		}
		else {
			gasStation = GasStationConverter.convertDtoToEntity(gasStationDto);
			
		}
		gasStationRepository.save(gasStation);
		return GasStationConverter.convertEntityToDto(gasStation);
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
	public List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, String gasolinetype,
			String carsharing) throws InvalidGasTypeException, GPSDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
			throws InvalidGasTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReport(Integer gasStationId, double dieselPrice, double superPrice, double superPlusPrice,
			double gasPrice, double methanePrice, Integer userId)
			throws InvalidGasStationException, PriceException, InvalidUserException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<GasStationDto> getGasStationByCarSharing(String carSharing) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	

}
