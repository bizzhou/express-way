package com.expressway.model;

import java.util.Date;
import java.util.LinkedList;

public class AirportNode {

    String name;                 // airport name
    double weight;               // flight duration
    boolean isVisited;
    LinkedList<Date> dates;

    public AirportNode(String name, double weight) {
        super();
        this.name = name;
        this.weight = weight;
        isVisited = false;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited (boolean isVisited) {
        this.isVisited = isVisited;
    }

}
