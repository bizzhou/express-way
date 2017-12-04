package com.expressway.model;

public class Reservation {

    private String accountNumber;
    private String reservationDate;
    private Double totalFare;
    private Double bookingFare;
    private Integer customerRepSSN;

    public Reservation(){
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public Double getBookingFare() {
        return bookingFare;
    }

    public void setBookingFare(Double bookingFare) {
        this.bookingFare = bookingFare;
    }

    public Integer getCustomerRepSSN() {
        return customerRepSSN;
    }

    public void setCustomerRepSSN(Integer customerRepSSN) {
        this.customerRepSSN = customerRepSSN;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "accountNumber='" + accountNumber + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                ", totalFare=" + totalFare +
                ", bookingFare=" + bookingFare +
                ", customerRepSSN=" + customerRepSSN +
                '}';
    }
}
