package com.expressway.model;

import java.util.Date;
import java.util.LinkedList;

public class AirportNode {

    String name;                 // airport name
    boolean isVisited;
    LinkedList<Date> dates;      // a list of departure dates, should be sorted

    public AirportNode(String name) {
        super();
        this.name = name;
        isVisited = false;
        dates = new LinkedList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited (boolean isVisited) {
        this.isVisited = isVisited;
    }

    public LinkedList<Date> getDates() {
        return dates;
    }

    public void setDates(Date date) {
        dates.add(date);
    }

}
