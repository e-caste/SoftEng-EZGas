package it.polito.ezgas.dto;


public class PriceReportDto {
    Integer gasStationId;
    Double dieselPrice;
    Double superPrice;
    Double superPlusPrice;
    Double gasPrice;
    Double methanePrice;
    Double premiumDieselPrice;
    Integer userId;

    public PriceReportDto(Integer gasStationId, Double dieselPrice, Double superPrice, Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId) {
        this.gasStationId = gasStationId;
        this.dieselPrice = dieselPrice;
        this.superPrice = superPrice;
        this.superPlusPrice = superPlusPrice;
        this.gasPrice = gasPrice;
        this.methanePrice = methanePrice;
        this.premiumDieselPrice = premiumDieselPrice;
        this.userId = userId;
    }

    public PriceReportDto() { }

    public Integer getGasStationId() { return gasStationId; }

    public void setGasStationId(Integer gasStationId) { this.gasStationId = gasStationId; }

    public Double getDieselPrice() { return dieselPrice; }

    public void setDieselPrice(Double dieselPrice) { this.dieselPrice = dieselPrice; }

    public Double getSuperPrice() { return superPrice; }

    public void setSuperPrice(Double superPrice) { this.superPrice = superPrice; }

    public Double getSuperPlusPrice() { return superPlusPrice; }

    public void setSuperPlusPrice(Double superPlusPrice) { this.superPlusPrice = superPlusPrice; }

    public Double getGasPrice() { return gasPrice; }

    public void setGasPrice(Double gasPrice) { this.gasPrice = gasPrice; }

    public Double getMethanePrice() { return methanePrice; }

    public void setMethanePrice(Double methanePrice) { this.methanePrice = methanePrice; }

    public Double getPremiumDieselPrice() { return premiumDieselPrice; }

    public void setPremiumDieselPrice(Double premiumDieselPrice) { this.premiumDieselPrice = premiumDieselPrice; }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}
