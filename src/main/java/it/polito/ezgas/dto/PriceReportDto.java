package it.polito.ezgas.dto;

import it.polito.ezgas.entity.User;

public class PriceReportDto {
	
	Integer priceReportId;
	User user;
	double dieselPrice;
	double superPrice;
	double superPlusPrice;
	double gasPrice;
	
	
	public PriceReportDto(Integer priceReportId, User user, double dieselPrice, double superPrice,
			double superPlusPrice, double gasPrice) {
		super();
		this.priceReportId = priceReportId;
		this.user = user;
		this.dieselPrice = dieselPrice;
		this.superPrice = superPrice;
		this.superPlusPrice = superPlusPrice;
		this.gasPrice = gasPrice;
	}


	public Integer getPriceReportId() {
		return priceReportId;
	}


	public void setPriceReportId(Integer priceReportId) {
		this.priceReportId = priceReportId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public double getDieselPrice() {
		return dieselPrice;
	}


	public void setDieselPrice(double dieselPrice) {
		this.dieselPrice = dieselPrice;
	}


	public double getSuperPrice() {
		return superPrice;
	}


	public void setSuperPrice(double superPrice) {
		this.superPrice = superPrice;
	}


	public double getSuperPlusPrice() {
		return superPlusPrice;
	}


	public void setSuperPlusPrice(double superPlusPrice) {
		this.superPlusPrice = superPlusPrice;
	}


	public double getGasPrice() {
		return gasPrice;
	}


	public void setGasPrice(double gasPrice) {
		this.gasPrice = gasPrice;
	}

}
