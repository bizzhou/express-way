package com.expressway.controller;

import com.expressway.model.FlightSearch;
import com.expressway.model.Leg;
import com.expressway.service.impl.FlightServiceImpl;
import com.expressway.service.impl.RouteSearchServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * get one way flight search results
     *
     * @param flightSearch
     * @return
     */
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

    @RequestMapping(value = "flight/search/round-trip", method = RequestMethod.POST)
    public ResponseEntity<Map> roundTripRouteSearch(@RequestBody final List<FlightSearch> flightSearches) {
        ArrayList<ArrayList<Leg>> routes = routeSearchService.searchRoutes(flightSearches.get(0));
        routes = flightService.getFareInformation(routes, flightSearches.get(0));

        ArrayList<ArrayList<Leg>> route2 = routeSearchService.searchRoutes(flightSearches.get(1));
        route2 = flightService.getFareInformation(route2, flightSearches.get(1));


        if (routes != null && route2 != null) {

            Map<String, ArrayList<ArrayList<Leg>>> map = new HashMap<>();
            map.put("from", routes);
            map.put("to", route2);

            return new ResponseEntity<Map>(map, HttpStatus.OK);

        }

        return new ResponseEntity<Map>(null, HttpStatus.BAD_REQUEST);

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



    //String airline, Integer flightNumber, String classType
    @RequestMapping(value = "/flight/remain-seats", method = RequestMethod.POST)
    public ResponseEntity<List> getRemainingSeats(@RequestParam("airline") String airline,
                                                  @RequestParam("flightNumber") Integer flightNumber,
                                                  @RequestParam("classType") String classType) {

        System.out.println(airline);

        List result = flightService.getRemainingSeats(airline, flightNumber, classType);

        if (result != null) {
            return new ResponseEntity<List>(result, HttpStatus.OK);
        }
        return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);

    }


}