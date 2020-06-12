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
		pdto.setDieselPrice(pr.getDieselPrice());
		pdto.setGasPrice(pr.getGasPrice());
		pdto.setSuperPlusPrice(pr.getSuperPlusPrice());
		pdto.setSuperPrice(pr.getSuperPrice());
				
		return pdto;
		
	}
	
	public PriceReport convertDtoToEntity(PriceReportDto pr) {
		PriceReport pr_entity = new PriceReport();
		pr_entity.setUser(userRepository.findOne(pr.getUserId()));
		//PriceReport Repository??
		pr_entity.setDieselPrice(pr.getDieselPrice());
		pr_entity.setGasPrice(pr.getGasPrice());
		pr_entity.setSuperPlusPrice(pr.getSuperPrice());
		pr_entity.setSuperPrice(pr.getSuperPrice());
		return pr_entity;
		
	}
}
