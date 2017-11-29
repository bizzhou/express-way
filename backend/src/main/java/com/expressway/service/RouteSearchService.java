package com.expressway.service;

import com.expressway.model.AirportNode;
import com.expressway.model.FlightSearch;

import java.util.ArrayList;

public interface RouteSearchService {

    ArrayList<ArrayList<AirportNode>> searchRoutes(FlightSearch flightSearch);

}
