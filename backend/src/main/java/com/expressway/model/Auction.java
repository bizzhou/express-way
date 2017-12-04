package com.expressway.model;

public class Auction {

    public String accountNumber;
    public String airlineId;
    public Integer flightNumber;
    public Integer legNumber;
    private String flightClass;
    private String depatureDate;
    private Double bidPrice;

    public Auction(){

    }

    public Integer getLegNumber() {
        return legNumber;
    }

    public void setLegNumber(Integer legNumber) {
        this.legNumber = legNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getDepatureDate() {
        return depatureDate;
    }

    public void setDepatureDate(String depatureDate) {
        this.depatureDate = depatureDate;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", airlineId='" + airlineId + '\'' +
                ", flightNumber=" + flightNumber +
                ", legNumber=" + legNumber +
                ", flightClass='" + flightClass + '\'' +
                ", depatureDate='" + depatureDate + '\'' +
                ", bidPrice=" + bidPrice +
                '}';
    }

}
