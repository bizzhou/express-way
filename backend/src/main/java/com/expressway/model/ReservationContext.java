package com.expressway.model;

import java.util.List;

public class ReservationContext {

    private Reservation reservation;
    private List<Include> includes;

    public ReservationContext() {

    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public List<Include> getIncludes() {
        return includes;
    }

    public void setIncludes(List<Include> includes) {
        this.includes = includes;
    }


    @Override
    public String toString() {
        return "ReservationContext{" +
                "reservation=" + reservation +
                ", includes=" + includes +
                '}';
    }
}

