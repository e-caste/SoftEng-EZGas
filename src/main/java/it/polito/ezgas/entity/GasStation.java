package it.polito.ezgas.entity;

import it.polito.ezgas.dto.GasStationDto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GasStation implements Serializable {

    private static final long serialVersionUID = 0x62A6DA99AABDA8A9L;
	
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer gasStationId;
    @Column
    private String gasStationName;
    @Column
    private String gasStationAddress;
    @Column
    private boolean hasDiesel;
    @Column
    private boolean hasSuper;
    @Column
    private boolean hasSuperPlus;
    @Column
    private boolean hasGas;
    @Column
    private boolean hasMethane;
    //*****************************************************************
	@Column
	private boolean hasPremiumDiesel;

    @Column
    private String carSharing;
    @Column
    private double lat;
    @Column
    private double lon;
    @Column
    private Double dieselPrice;
    @Column
    private Double superPrice;
    @Column
    private Double superPlusPrice;
    @Column
    private Double gasPrice;
    @Column
    private Double methanePrice;
    //**********************************************************
	@Column
	private Double premiumDieselPrice;
    @Column
    private Integer reportUser;
    @Column
    private String reportTimestamp;
    @Column
    private double reportDependability;

    @ManyToOne(optional = true)
    @JoinColumn(name = "USER_ID")
    private User user;
    
    //serve lista di fuels

	public GasStation() {}

	public GasStation(String gasStationName, String gasStationAddress, boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane, String carSharing, double lat, double lon, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, Integer reportUser, String reportTimestamp, double reportDependability) {
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
		this.user = null;
		this.reportTimestamp = reportTimestamp;
		this.reportDependability = reportDependability;
	}

	public boolean equals(GasStation other) {
		return this.gasStationName.equals(other.getGasStationName()) &&
				this.gasStationAddress.equals(other.getGasStationAddress()) &&
				(this.carSharing == null || this.carSharing.equals(other.getCarSharing())) &&
				this.hasDiesel == other.getHasDiesel() &&
				this.hasPremiumDiesel == other.getHasPremiumDiesel() &&
				this.hasGas == other.getHasGas() &&
				this.hasMethane == other.getHasMethane() &&
				this.hasSuper == other.getHasSuper() &&
				this.lat == other.getLat() &&
				this.lon == other.getLon() &&
				this.dieselPrice.equals(other.getDieselPrice()) &&
				this.premiumDieselPrice.equals(other.getPremiumDieselPrice()) &&
				this.superPrice.equals(other.getSuperPrice()) &&
				this.superPlusPrice.equals(other.getSuperPlusPrice()) &&
				this.gasPrice.equals(other.getGasPrice()) &&
				this.methanePrice.equals(other.getMethanePrice()) &&
				(this.reportUser == null || this.reportUser.equals(other.getReportUser())) &&
//				this.user.equals(other.getUser()) &&
				(this.reportTimestamp == null || this.reportTimestamp.equals(other.getReportTimestamp())) &&
				this.reportDependability == other.getReportDependability();
	}

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
    
    /*public GasStation(String gasStationName, String gasStationAddress, boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane, String carSharing, double lat, double lon, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice, double methanePrice, Integer reportUser, String reportTimestamp, double reportDependability) {
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
		this.user = null;
		this.reportTimestamp = reportTimestamp;
		this.reportDependability = reportDependability;
    }*/

	public GasStation(String gasStationName, String gasStationAddress,
					  boolean hasDiesel, boolean hasSuper, boolean hasSuperPlus, boolean hasGas, boolean hasMethane,
					  boolean hasPremiumDiesel, String carSharing, double lat, double lon,
					  Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice,
					  Double premiumDieselPrice, Integer reportUser, String reportTimestamp, double reportDependability) {
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
		this.user = null;
		this.reportTimestamp = reportTimestamp;
		this.reportDependability = reportDependability;
	}
    
    
	public double getReportDependability() {
		return reportDependability;
	}

	public void setReportDependability(double reportDependability) {
		this.reportDependability = reportDependability;
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

	public boolean getHasDiesel() {
		return hasDiesel;
	}

	public void setHasDiesel(boolean hasDiesel) {
		this.hasDiesel = hasDiesel;
	}

	public boolean getHasSuper() {
		return hasSuper;
	}

	public void setHasSuper(boolean hasSuper) {
		this.hasSuper = hasSuper;
	}

	public boolean getHasSuperPlus() {
		return hasSuperPlus;
	}

	public void setHasSuperPlus(boolean hasSuperPlus) {
		this.hasSuperPlus = hasSuperPlus;
	}

	public boolean getHasGas() {
		return hasGas;
	}

	public void setHasGas(boolean hasGas) {
		this.hasGas = hasGas;
	}

	//*****************************************************************
	public boolean getHasPremiumDiesel() { return hasPremiumDiesel; }

	public void setHasPremiumDiesel(boolean hasPremiumDiesel) {
		this.hasPremiumDiesel = hasPremiumDiesel;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
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

	//******************************************************************
	public Double getPremiumDieselPrice() {
		return premiumDieselPrice;
	}

	public void setPremiumDieselPrice(Double premiumDieselPrice) {
		this.premiumDieselPrice = premiumDieselPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getCarSharing() {
		return carSharing;
	}

	public void setCarSharing(String carSharing) {
		this.carSharing = carSharing;
	}
}
