package it.polito.ezgas.converter;

import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.entity.PriceReport;
import it.polito.ezgas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class PriceReportConverter {

	public PriceReportConverter() {
		
	}

	@Autowired
	UserRepository userRepository;



	public PriceReportDto convertEntityToDto(PriceReport pr) {
		PriceReportDto pdto = new PriceReportDto();
		pdto.setUserId(pr.getUser().getUserId());
		//missing getGasStationid in price report entity
		//pdto.setGasStationId(pr.getGasStationId());
		pdto.setDieselPrice(pr.getDieselPrice());
		pdto.setGasPrice(pr.getGasPrice());
		pdto.setSuperPlusPrice(pr.getSuperPlusPrice());
		pdto.setSuperPrice(pr.getSuperPrice());
		//missing getMethanePrice
		//pdto.setMethanePrice(pr.getMethanePrice());
		//missing getPremiumDieselPrice in entity
		//pdto.setPremiumDieselPrice(pr.getPremiumDieselPrice());
		return pdto;
		
	}
	
	public PriceReport convertDtoToEntity(PriceReportDto pr) {
		PriceReport prEntity = new PriceReport();
		prEntity.setUser(userRepository.findById(pr.getUserId()));
		//PriceReport Repository??
		//missing setGSid in entity
		prEntity.setDieselPrice(pr.getDieselPrice());
		prEntity.setGasPrice(pr.getGasPrice());
		prEntity.setSuperPlusPrice(pr.getSuperPrice());
		prEntity.setSuperPrice(pr.getSuperPrice());
		//missing setPremiumDieselPrice in entity

		return prEntity;
		
	}
}
