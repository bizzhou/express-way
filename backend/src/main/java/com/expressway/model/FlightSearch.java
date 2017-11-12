package com.expressway.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FlightSearch {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;

    private String fromAirport;
    private String toAirport;
    private String fareType;
    private String classType;

    public FlightSearch(){
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", fromAirport='" + fromAirport + '\'' +
                ", toAirport='" + toAirport + '\'' +
                ", fareType='" + fareType + '\'' +
                ", classType='" + classType + '\'' +
                '}';
    }
}
