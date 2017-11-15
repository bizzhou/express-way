package com.expressway.model;

import java.util.Date;

public class Flight {

    private int flightNumber;
    private String airline;
    private int seatCapacity;
    private int stops;
    private Date startTime;
    private Date endTime;
    private int maxLengthStay;
    private int minLengthStay;
    private String dateOfWeek;

    public Flight() {
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber=" + flightNumber +
                ", airline='" + airline + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", stops=" + stops +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", maxLengthStay=" + maxLengthStay +
                ", minLengthStay=" + minLengthStay +
                ", dateOfWeek='" + dateOfWeek + '\'' +
                '}';
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getMaxLengthStay() {
        return maxLengthStay;
    }

    public void setMaxLengthStay(int maxLengthStay) {
        this.maxLengthStay = maxLengthStay;
    }

    public int getMinLengthStay() {
        return minLengthStay;
    }

    public void setMinLengthStay(int minLengthStay) {
        this.minLengthStay = minLengthStay;
    }

    public String getDateOfWeek() {
        return dateOfWeek;
    }

    public void setDateOfWeek(String dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }
}
