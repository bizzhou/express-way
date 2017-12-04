package com.expressway.model;

public class ReservationContext {

    private Reservation reservation;
    private Include include;

    public ReservationContext(){

    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Include getInclude() {
        return include;
    }

    public void setInclude(Include include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "ReservationContext{" +
                "reservation=" + reservation +
                ", include=" + include +
                '}';
    }
}
