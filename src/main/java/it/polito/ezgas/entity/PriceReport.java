package it.polito.ezgas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PriceReport {
	
    private static final long serialVersionUID = 0x62A6DA99AABDA8A9L;

    @Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer priceReportId;
    @ManyToOne
    private User user;
	
	
	@Column
	private double dieselPrice;

	@Column
	private double superPrice;
	@Column
	private double superPlusPrice;
	@Column
	private double gasPrice;
	
	
	
	
	
	
	public PriceReport(User user, double dieselPrice, double superPrice, double superPlusPrice, double gasPrice) {
		super();
		this.user = user;
		this.dieselPrice = dieselPrice;
		this.superPrice = superPrice;
		this.superPlusPrice = superPlusPrice;
		this.gasPrice = gasPrice;
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



	public Integer getPriceReportId() {
		return priceReportId;
	}



	public void setPriceReportId(Integer priceReportId) {
		this.priceReportId = priceReportId;
	}
	
	
	
	
	
	

}
