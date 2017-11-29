package com.expressway.service.impl;

import com.expressway.model.AirportGraph;
import com.expressway.model.AirportNode;
import com.expressway.model.FlightSearch;
import com.expressway.model.Leg;
import com.expressway.service.FlightService;
import com.expressway.service.RouteSearchService;
import com.expressway.util.ConnectionUtil;
import com.expressway.util.Helper;
import com.expressway.util.RouteSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RouteSearchServiceImpl implements RouteSearchService{

    @Autowired
    private FlightService flightService;

    @Autowired
    private Helper helper;

    @Autowired
    private RouteSearchUtil routeSearchUtil;

    /**
     *
     * @param flightSearch
     * @return a list of routes with necessary info,
     * i.e SFO (with leg info) -> LAX (with leg info) -> JFK (with leg info)
     */
    @Override
    public ArrayList<ArrayList<AirportNode>> searchRoutes(FlightSearch flightSearch) {

        AirportGraph airportGraph = routeSearchUtil.createRouteGraph();
        ArrayList<ArrayList<AirportNode>> routes = airportGraph.getPaths(flightSearch.getFromAirport(), flightSearch.getToAirport());
//        routes = filterRoutes(routes);
        return routes;
    }

    // TEST METHOD
    public ArrayList<ArrayList<AirportNode>> searchRoutes(String fromAirport, String toAirport) {

        AirportGraph airportGraph = routeSearchUtil.createRouteGraph();

        // get a list of possible routes
        ArrayList<ArrayList<AirportNode>> routes = airportGraph.getPaths(fromAirport, toAirport);

        // add leg info
        routeSearchUtil.addLegInfoToRoute(routes);

        // 

        return routes;
    }


}
