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
    public ArrayList<ArrayList<Leg>> searchRoutes(String fromAirport, String toAirport) {

        AirportGraph airportGraph = routeSearchUtil.createRouteGraph();

        // get a list of possible paths
        // ex: paths[0][0]=JFK, paths[0][1]=BOS, paths[0][2]=SFO
        //     paths[1][0]=JFK, paths[1][1]=SFO
        ArrayList<ArrayList<AirportNode>> paths = airportGraph.getPaths(fromAirport, toAirport);

        // add leg info
        ArrayList<ArrayList<Leg>> routes = getRoutesFromPaths(paths);

        // get flight info (exact routes) from paths
//        ArrayList<ArrayList<Leg>> routes = getRoutesFromPaths(paths);

        return routes;
    }


    // TODO date must be in sequence
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
                // TODO should add multiple legs (as database grows)
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
