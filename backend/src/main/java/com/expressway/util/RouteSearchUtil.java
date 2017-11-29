package com.expressway.util;

import com.expressway.model.AirportGraph;
import com.expressway.model.AirportNode;
import com.expressway.model.Leg;
import com.expressway.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RouteSearchUtil {

    @Autowired
    FlightService flightService;

    public void addLegInfoToRoute(ArrayList<ArrayList<AirportNode>> routes) {

        for (ArrayList<AirportNode> route : routes) {

            int i = 0;
            while (i < route.size()-1) {
                // get legs from db
                ArrayList<Leg> legs = flightService.getLegsByAirport(route.get(i).getName(),
                        route.get(i+1).getName());
                // add leg to current route (i.e: add flight info from JFK -> SFO)
                route.get(i).setLegs(legs);

                i++;
            }
        }
    }

    public ArrayList<ArrayList<AirportNode>> filterRoutes(ArrayList<ArrayList<AirportNode>> routes) {

        // if a route contains more than 5 legs, exclude it
        for (ArrayList<AirportNode> node : routes) {
            if (node.size() >= 5)
                routes.remove(node);
        }
        // date must be in sequence


        return routes;
    }


    public AirportGraph createRouteGraph() {

        // Map < from_airport, airport_name >, Map < to_airport, airport_name >
        List<Map<String, String>> legList = flightService.getAllLegs();

        AirportGraph airportGraph = new AirportGraph();

        // add nodes
        for (int i = 0; i < legList.size(); i++) {

            AirportNode from = new AirportNode(legList.get(i).get("from_airport"));
            AirportNode to = new AirportNode(legList.get(i).get("to_airport"));
//            Leg legInfo = flightService.getLegByAirport(legList.get(i).get("from_airport"), legList.get(i).get("to_airport"));

            airportGraph.addNewNode(from, to);

        }

        return airportGraph;
    }
}
