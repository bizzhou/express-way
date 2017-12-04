package com.expressway.model;

public class Include {

    private String reservationNumber;
    private String airlineId;
    private Integer flightNumber;
    private Integer legNumber;
    private String lastName;
    private String firstName;
    private String deptDate;
    private Integer seatNumber;
    private String flightClass;
    private String meal;
    private Integer fromStop;

    public Include(){
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
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

    public String getLastNmae() {
        return lastName;
    }

    public void setLastNmae(String lastNmae) {
        this.lastName = lastNmae;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDeptDate() {
        return deptDate;
    }

    public void setDeptDate(String deptDate) {
        this.deptDate = deptDate;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public Integer getFromStop() {
        return fromStop;
    }

    public void setFromStop(Integer fromStop) {
        this.fromStop = fromStop;
    }


    public Integer getLegNumber() {
        return legNumber;
    }

    public void setLegNumber(Integer legNumber) {
        this.legNumber = legNumber;
    }

    @Override
    public String toString() {
        return "Include{" +
                "reservationNumber='" + reservationNumber + '\'' +
                ", airlineId='" + airlineId + '\'' +
                ", flightNumber=" + flightNumber +
                ", legNumber='" + legNumber + '\'' +
                ", lastNmae='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", deptDate='" + deptDate + '\'' +
                ", seatNumber=" + seatNumber +
                ", flightClass='" + flightClass + '\'' +
                ", meal='" + meal + '\'' +
                ", fromStop=" + fromStop +
                '}';
    }
}
