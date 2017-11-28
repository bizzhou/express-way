package com.expressway.service.impl;

import com.expressway.model.AirportGraph;
import com.expressway.model.AirportNode;
import com.expressway.service.FlightService;
import com.expressway.service.RouteSearchService;
import com.expressway.util.ConnectionUtil;
import com.expressway.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteSearchServiceImpl {

    // 1. get flight data from db
    // 2. use data to create nodes
    // 3. create graph
    // 4. search paths

    @Autowired
    private ConnectionUtil connectionUtil;

    @Autowired
    private Helper helper;

    @Autowired
    private FlightService flightService;

//    public void searchRoute(String departureAirport, String departureDate,
//                            String destinationAirport, String returnDate) {
//
//    }

    public ArrayList<ArrayList<AirportNode>> searchRoute(String fromAirport, String toAirport) {

        AirportGraph airportGraph = createRouteGraph();
        return airportGraph.getRoutes(fromAirport, toAirport);

    }

    public AirportGraph createRouteGraph() {

        // Map < from_airport, airport_name >, Map < to_airport, airport_name >
        List<Map<String, String>> legList = flightService.getAllLegs();

        AirportGraph airportGraph = new AirportGraph();

        // add nodes
        for (int i = 0; i < legList.size(); i++) {

            AirportNode from = new AirportNode(legList.get(i).get("from_airport"));
            AirportNode to = new AirportNode(legList.get(i).get("to_airport"));

            airportGraph.addNewNode(from, to);

        }

        return airportGraph;
    }

}
