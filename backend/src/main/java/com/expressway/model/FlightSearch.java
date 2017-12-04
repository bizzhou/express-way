package com.expressway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FlightSearch {

    private String depatureDate;
    private String returnDate;

    private String fromAirport;
    private String toAirport;
    private String fareType;
    private String classType;
    private boolean isFlexible;

    public FlightSearch() {
    }

    public boolean getIsFlexible() { return isFlexible; }

    public void setIsFlexible(boolean isFlexible) { this.isFlexible = isFlexible; }

    public String getDepatureDate() {
        return depatureDate;
    }

    public void setDepatureDate(String depatureDate) {
        this.depatureDate = depatureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "FlightSearch{" +
                "depatureDate='" + depatureDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", fromAirport='" + fromAirport + '\'' +
                ", toAirport='" + toAirport + '\'' +
                ", fareType='" + fareType + '\'' +
                ", classType='" + classType + '\'' +
                '}';
    }
}
