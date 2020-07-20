package it.polito.ezgas.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import it.polito.ezgas.entity.GasStation;
import it.polito.ezgas.entity.User;

public class GasStationDto {

	Integer gasStationId;
	String gasStationName;
	String gasStationAddress;
	boolean hasDiesel;
    boolean hasSuper;
    boolean hasSuperPlus;
    boolean hasGas;
    boolean hasMethane;
    //****************************
	boolean hasPremiumDiesel;
    private String carSharing;
    double lat;
    double lon;
    Double dieselPrice;
    Double superPrice;
    Double superPlusPrice;
    Double gasPrice;
    Double methanePrice;
    //****************************************
	Double premiumDieselPrice;

    Integer reportUser;
    UserDto userDto;
    String reportTimestamp;
    double reportDependability;

	/*
	public GasStationDto(Integer gasStationId, String gasStationName, String gasStationAddress, 
			boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane, String carSharing, 
			double lat, double lon, 
			double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, 
			Integer reportUser, String reportTimestamp, double reportDependability) {
		this.gasStationId = gasStationId;
		this.gasStationName = gasStationName;
		this.gasStationAddress = gasStationAddress;
		this.carSharing = carSharing;
		this.hasDiesel = hasDiesel;
		this.hasGas = hasGas;
		this.hasMethane = hasMethane;
		this.hasSuper = hasSuper;
		this.hasSuperPlus = hasSuperPlus;
		this.lat = lat;
		this.lon = lon;
		this.dieselPrice = dieselPrice;
		this.superPrice = superPrice;
		this.superPlusPrice = superPlusPrice;
		this.gasPrice = gasPrice;
		this.methanePrice = methanePrice;
		this.reportUser = reportUser;
		this.userDto = null;
		this.reportTimestamp = reportTimestamp;
		this.reportDependability = reportDependability;
		
		
	}*/
	
	//***********************************************************************
	public GasStationDto(Integer gasStationId, String gasStationName, String gasStationAddress,
						 boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane,
						 boolean hasPremiumDiesel, String carSharing, double lat, double lon,
						 Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice,
						 Double premiumDieselPrice, Integer reportUser, String reportTimestamp, double reportDependability) {
		this.gasStationId = gasStationId;
		this.gasStationName = gasStationName;
		this.gasStationAddress = gasStationAddress;
		this.carSharing = carSharing;
		this.hasDiesel = hasDiesel;
		this.hasGas = hasGas;
		this.hasMethane = hasMethane;
		this.hasSuper = hasSuper;
		this.hasSuperPlus = hasSuperPlus;
		this.hasPremiumDiesel = hasPremiumDiesel;
		this.lat = lat;
		this.lon = lon;
		this.dieselPrice = dieselPrice;
		this.superPrice = superPrice;
		this.superPlusPrice = superPlusPrice;
		this.gasPrice = gasPrice;
		this.methanePrice = methanePrice;
		this.premiumDieselPrice = premiumDieselPrice;
		this.reportUser = reportUser;
		this.userDto = null;
		this.reportTimestamp = reportTimestamp;
		this.reportDependability = reportDependability;
	}

	public boolean equals(GasStationDto other) {
		boolean test1 = (this.hasDiesel == other.getHasDiesel());
		if(test1){
			if(this.hasDiesel)
				test1 = this.dieselPrice.equals(other.getDieselPrice());
		}
		boolean test2 = (this.hasPremiumDiesel == other.getHasPremiumDiesel());
		if(test2){
			if(this.hasPremiumDiesel)
				test2 = this.premiumDieselPrice.equals(other.getPremiumDieselPrice());
		}
		boolean test3 = (this.hasGas == other.getHasGas());
		if(test3){
			if(this.hasGas)
				test3 = this.gasPrice.equals(other.getGasPrice());
		}
		boolean test4 = (this.hasMethane == other.getHasMethane());
		if(test4){
			if(this.hasMethane)
				test4 = this.methanePrice.equals(other.getMethanePrice());
		}
		boolean test5 = (this.hasSuper == other.getHasSuper());
		if(test5){
			if(this.hasSuper)
				test5 = this.superPrice.equals(other.getSuperPrice());
		}
		boolean test6 = (this.hasSuperPlus == other.getHasSuperPlus());
		if(test6){
			if(this.hasSuperPlus)
				test6 = this.superPlusPrice.equals(other.getSuperPlusPrice());
		}

		return this.gasStationName.equals(other.getGasStationName()) &&
				this.gasStationAddress.equals(other.getGasStationAddress()) &&
				(this.carSharing == null || this.carSharing.equals(other.getCarSharing())) &&
				test1 &&
				test2 &&
				test3 &&
				test4 &&
				test5 &&
				test6 &&
				this.lat == other.getLat() &&
				this.lon == other.getLon() &&

				(this.reportUser == null || this.reportUser.equals(other.getReportUser())) &&
				(this.reportTimestamp == null || this.reportTimestamp.equals(other.getReportTimestamp())) &&
				this.reportDependability == other.getReportDependability();
	}

	public double getReportDependability() {
		return reportDependability;
	}

	public void setReportDependability(double reportDependability) {
		this.reportDependability = reportDependability;
	}

	public GasStationDto() { }

	public Integer getGasStationId() {
		return gasStationId;
	}

	public void setGasStationId(Integer gasStationId) {
		this.gasStationId = gasStationId;
	}

	public String getGasStationName() {
		return gasStationName;
	}

	public void setGasStationName(String gasStationName) {
		this.gasStationName = gasStationName;
	}
	
	public String getGasStationAddress() {
		return gasStationAddress;
	}
	
	public void setGasStationAddress(String gasStationAddress) {
		this.gasStationAddress = gasStationAddress;
	}

	public boolean getHasDiesel() {
		return hasDiesel;
	}

	public void setHasDiesel(boolean hasDiesel) {
		this.hasDiesel = hasDiesel;
	}

	public Boolean getHasSuper() {
		return hasSuper;
	}

	public void setHasSuper(Boolean hasSuper) {
		this.hasSuper = hasSuper;
	}

	public Boolean getHasSuperPlus() {
		return hasSuperPlus;
	}

	public void setHasSuperPlus(Boolean hasSuperPlus) {
		this.hasSuperPlus = hasSuperPlus;
	}

	public Boolean getHasGas() {
		return hasGas;
	}

	public void setHasGas(Boolean hasGas) {
		this.hasGas = hasGas;
	}
//*************************************************************************
	public boolean getHasPremiumDiesel() { return hasPremiumDiesel; }

	public void setHasPremiumDiesel(boolean hasPremiumDiesel) {
		this.hasPremiumDiesel = hasPremiumDiesel;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getDieselPrice() {
		return dieselPrice;
	}

	public void setDieselPrice(Double dieselPrice) {
		this.dieselPrice = dieselPrice;
	}

	public Double getSuperPrice() {
		return superPrice;
	}

	public void setSuperPrice(Double superPrice) {
		this.superPrice = superPrice;
	}

	public Double getSuperPlusPrice() {
		return superPlusPrice;
	}

	public void setSuperPlusPrice(Double superPlusPrice) {
		this.superPlusPrice = superPlusPrice;
	}

	public Double getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(Double gasPrice) {
		this.gasPrice = gasPrice;
	}
//****************************************************************************
	public Double getPremiumDieselPrice() {
		return premiumDieselPrice;
	}

	public void setPremiumDieselPrice(Double premiumDieselPrice) {
		this.premiumDieselPrice = premiumDieselPrice;
	}

	public Integer getReportUser() {
		return reportUser;
	}

	public void setReportUser(Integer reportUser) {
		this.reportUser = reportUser;
	}

	public String getReportTimestamp() {
		return reportTimestamp;
	}

	public void setReportTimestamp(String reportTimestamp) {
		this.reportTimestamp = reportTimestamp;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public boolean getHasMethane() {
		return hasMethane;
	}

	public void setHasMethane(boolean hasMethane) {
		this.hasMethane = hasMethane;
	}

	public Double getMethanePrice() {
		return methanePrice;
	}

	public void setMethanePrice(Double methanePrice) {
		this.methanePrice = methanePrice;
	}

	public void setHasSuper(boolean hasSuper) {
		this.hasSuper = hasSuper;
	}

	public void setHasSuperPlus(boolean hasSuperPlus) {
		this.hasSuperPlus = hasSuperPlus;
	}

	public void setHasGas(boolean hasGas) {
		this.hasGas = hasGas;
	}

	public String getCarSharing() {
		return carSharing;
	}

	public void setCarSharing(String carSharing) {
		this.carSharing = carSharing;
	}

}
