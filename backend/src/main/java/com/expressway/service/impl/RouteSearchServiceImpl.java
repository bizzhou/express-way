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


    @Override
    public ArrayList<ArrayList<Leg>> searchRoutes(FlightSearch flightSearch) {

        // create graph with all connected airports
        AirportGraph airportGraph = routeSearchUtil.createRouteGraph();

        // get a list of possible paths (airports)
        ArrayList<ArrayList<AirportNode>> paths = airportGraph.getPaths(
                flightSearch.getFromAirport(), flightSearch.getToAirport());

        // get a list of routes, a route consists of a sequence of legs
        ArrayList<ArrayList<Leg>> routes = getRoutesFromPaths(paths);

        // make sure dates are in sequence
        routes = routeSearchUtil.filterRoutes(routes, flightSearch);

        return routes;
    }


//
//    public ArrayList<ArrayList<Leg>> searchRoutes(String fromAirport, String toAirport) {
//
//        // create graph with all connected airports
//        AirportGraph airportGraph = routeSearchUtil.createRouteGraph();
//
//        // get a list of possible paths (airports)
//        ArrayList<ArrayList<AirportNode>> paths = airportGraph.getPaths(fromAirport, toAirport);
//
//        // get a list of routes, a route consists of a sequence of legs
//        ArrayList<ArrayList<Leg>> routes = getRoutesFromPaths(paths);
//
//        // make sure dates are in sequence
//        routes = routeSearchUtil.filterRoutes(routes);
//
//        return routes;
//    }

    private ArrayList<ArrayList<Leg>> getRoutesFromPaths(ArrayList<ArrayList<AirportNode>> paths) {

        ArrayList<ArrayList<Leg>> routes = new ArrayList<>();
        ArrayList<Leg> route;
        ArrayList<AirportNode> path;

        for (int pathIndex = 0; pathIndex < paths.size(); pathIndex++) {

            int i = 0;

            route = new ArrayList<>();
            path = paths.get(pathIndex);
            while (i < path.size()-1) {
                // get legs from db
                ArrayList<Leg> legs = flightService.getLegsByAirport(path.get(i).getName(),
                        path.get(i+1).getName());
                // add legs to current route
//                path.get(i).setLegs(legs);
                // TODO should add multiple legs (if database gets expanded)
                route.add(i, legs.get(0));
                i++;
            }
            routes.add(pathIndex, route);
        }
        return routes;
    }

//    private ArrayList<ArrayList<Leg>> getRoutesFromPaths(ArrayList<ArrayList<AirportNode>> paths) {
//
//        ArrayList<ArrayList<Leg>> routes = new ArrayList<>();
//        ArrayList<Leg> route = null;
//
//        for (int pathIndex = 0; pathIndex < paths.size(); pathIndex++) {
//
//            route = new ArrayList<>();
//            int pathSize = paths.get(pathIndex).size()-1;
//            for (int nodeIndex = 0; nodeIndex < pathSize; nodeIndex++) {
//
//                route.add(nodeIndex, paths.get(pathIndex).get(nodeIndex).getLegs().get(0));
//
//            }
//            routes.add(route);
//
//        }
//
//        return routes;
//
//    }

}
