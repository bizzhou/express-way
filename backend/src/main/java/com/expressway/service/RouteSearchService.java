package com.expressway.service;

import com.expressway.model.AirportNode;
import com.expressway.model.FlightSearch;
import com.expressway.model.Leg;

import java.util.ArrayList;

public interface RouteSearchService {

    ArrayList<ArrayList<Leg>> searchRoutes(FlightSearch flightSearch);

}
