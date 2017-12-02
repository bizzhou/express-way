package com.expressway.controller;


import com.expressway.model.AirportNode;
import com.expressway.model.FlightSearch;
import com.expressway.model.Leg;
import com.expressway.service.RouteSearchService;
import com.expressway.service.impl.FlightServiceImpl;
import com.expressway.service.impl.RouteSearchServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FlightApiController {

    @Autowired
    private FlightServiceImpl flightService;

    @Autowired
    private RouteSearchServiceImpl routeSearchService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(FlightApiController.class);

    @RequestMapping(value = "/flight/search", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<ArrayList>> RouteSearch(@RequestBody final FlightSearch flightSearch) {
        System.out.println(flightSearch.getFromAirport() + " " + flightSearch.getToAirport());
        ArrayList<ArrayList<Leg>> routes = routeSearchService.searchRoutes(flightSearch);
        routes = flightService.getFareInformation(routes, flightSearch);
        if (routes != null) {
            return new ResponseEntity(routes, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Get a list of all customers who have seats reserved on a given flight
     *
     * @param airline
     * @param flightNumber
     * @return
     * @throws SQLException sample access: http://localhost:8080/flight/get-seats-reserved-on-flight?airline=JA&flightNumber=111
     */
    @RequestMapping(value = "/flight/get-seats-reserved-on-flight", method = RequestMethod.GET)
    public ResponseEntity<List> getSeatsReservedOnFlight(@RequestParam("airline") String airline,
                                                         @RequestParam("flightNumber") int flightNumber) throws SQLException {

        List<String> customerAccts = flightService.getSeatsReservedOnFlight(airline, flightNumber);

        if (customerAccts != null)
            return new ResponseEntity<List>(customerAccts, HttpStatus.OK);
        return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);

    }

}