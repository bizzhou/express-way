package com.expressway.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class AirportNode implements Cloneable{

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

    public void addLeg(Leg leg) {
        legs.add(leg);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
