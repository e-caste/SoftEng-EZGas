package it.polito.ezgas.converter;

import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.entity.PriceReport;



public class PriceReportConverter {

	public PriceReportConverter() {
		
	}
		

	public PriceReportDto convertEntityToDto(PriceReport pr) {
		PriceReportDto pdto = new PriceReportDto();
		pdto.setUser(pr.getUser());
		pdto.setPriceReportId(pr.getPriceReportId());
		pdto.setDieselPrice(pr.getDieselPrice());
		pdto.setGasPrice(pr.getGasPrice());
		pdto.setSuperPlusPrice(pr.getSuperPlusPrice());
		pdto.setSuperPrice(pr.getSuperPrice());
				
		return pdto;
		
	}
	
	public PriceReport convertDtoToEntity(PriceReportDto pr) {
		PriceReport pr_entity = new PriceReport();
		pr_entity.setUser(pr.getUser());
		pr_entity.setPriceReportId(pr.getPriceReportId());
		pr_entity.setDieselPrice(pr.getDieselPrice());
		pr_entity.setGasPrice(pr.getGasPrice());
		pr_entity.setSuperPlusPrice(pr.getSuperPrice());
		pr_entity.setSuperPrice(pr.getSuperPrice());
		return pr_entity;
		
	}
}
