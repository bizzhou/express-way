package com.expressway.util;

import com.expressway.model.AirportGraph;
import com.expressway.model.AirportNode;
import com.expressway.model.FlightSearch;
import com.expressway.model.Leg;
import com.expressway.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class RouteSearchUtil {

    @Autowired
    FlightService flightService;

    @Autowired
    Helper helper;

    public ArrayList<ArrayList<Leg>> filterRoutes(ArrayList<ArrayList<Leg>> routes, FlightSearch flightSearch) {

        // if a route contains more than 5 legs, exclude it
        for (ArrayList<Leg> route : routes) {
            if (route.size() >= 5)
                routes.remove(route);
        }

        // date from db must be after the user selected date
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i).size() > 0) {
                ArrayList<Leg> route = routes.get(i);
                Date departTime = helper.convertStringToDate(route.get(0).getDepartureTime());
                Date selectedDepartTime = helper.convertStringToDate(flightSearch.getDepatureDate());
                // remove this route if necessary
                System.out.println(departTime);
                System.out.println(selectedDepartTime);

                if (departTime.before(selectedDepartTime)) {
                    routes.remove(route);
                    // i-- because after removal, routes size decremented
                    i--;
                }
            }
        }

        // date must not overlap
        for (int i = 0; i < routes.size(); i++) {

            if (routes.get(i).size() > 0) {

                // if current leg's arrival time is after next leg's departure time,
                // remove this route
                ArrayList<Leg> route = routes.get(i);
                for (int j = 0; j < route.size()-1; j++) {
                    Date currArrvlTime = helper.convertStringToDate(route.get(j).getArrivalTime());
                    Date nextDptTime = helper.convertStringToDate(route.get(j+1).getDepartureTime());
                    // remove this route
                    if (currArrvlTime.after(nextDptTime)) {
                        routes.remove(route);
                        break;
                    }

                }
            }
        }

        return routes;
    }

    public ArrayList<ArrayList<Leg>> handleFlexibleRoutes(ArrayList<ArrayList<Leg>> routes, FlightSearch flightSearch) {
        return null;
    }

    public ArrayList<ArrayList<Leg>> handleClassType(ArrayList<ArrayList<Leg>> routes, FlightSearch flightSearch) {
        return null;
    }

    public AirportGraph createRouteGraph() {


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
