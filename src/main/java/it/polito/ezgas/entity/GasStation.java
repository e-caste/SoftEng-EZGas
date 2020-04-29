package it.polito.ezgas.entity;

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
    
    @Column
    private String carSharing;
    @Column
    private double lat;
    @Column
    private double lon;
    @Column
    private double dieselPrice;
    @Column
    private double superPrice;
    @Column
    private double superPlusPrice;
    @Column
    private double gasPrice;
    @Column
    private double methanePrice;
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

    
    
    public GasStation() {
    	
    }
    
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

	public double getMethanePrice() {
		return methanePrice;
	}

	public void setMethanePrice(double methanePrice) {
		this.methanePrice = methanePrice;
	}

	public String getCarSharing() {
		return carSharing;
	}

	public void setCarSharing(String carSharing) {
		this.carSharing = carSharing;
	}

	
	
	
	
	

	
	
	
	
}
