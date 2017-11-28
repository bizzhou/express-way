package com.expressway.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class AirportNode {

    private String name;                 // airport name
    private boolean isVisited;
    private ArrayList<Leg> legs;

    public AirportNode(String name) {
        super();
        this.name = name;
        isVisited = false;
        legs = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited (boolean isVisited) {
        this.isVisited = isVisited;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }
}
