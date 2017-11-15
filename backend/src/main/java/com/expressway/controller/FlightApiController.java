package com.expressway.controller;


import com.expressway.model.FlightSearch;
import com.expressway.service.impl.FlightServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FlightApiController {

    @Autowired
    private FlightServiceImpl flightService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(FlightApiController.class);

    @RequestMapping(value = "/flight/search", method = RequestMethod.POST)
    public ResponseEntity<List> flightSearch(@RequestBody final FlightSearch flightSearch) throws IOException {

        List result;

        logger.info("********************************************************************************");


        if ((result = flightService.serachFlight(flightSearch)) != null) {

            return new ResponseEntity<List>(result, HttpStatus.OK);

        } else

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Get a list of most frequent flights
     * @return
     * @throws SQLException
     */
    @RequestMapping(value="/flight/most-freq-flights", method = RequestMethod.GET)
    public ResponseEntity<List> getMostFreqFlights() throws SQLException {
        List<Map<String, Object>> result;

        if ((result = flightService.getMostFreqFlights()) != null) {

            return new ResponseEntity<List>(result, HttpStatus.OK);

        } else {

            return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Get flights for a given airport
     * @param airportId
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/flight/get-flights-for-airport/{airportId}", method = RequestMethod.GET)
    public ResponseEntity<List> getFlightsForAirport(@PathVariable("airportId") String airportId) throws SQLException{

        List<Map<String, Object>> result = flightService.getFlightsForAirport(airportId);

        if (result != null)
            return new ResponseEntity<List>(result, HttpStatus.OK);

        return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Get a list of all customers who have seats reserved on a given flight
     * @param airline
     * @param flightNumber
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/flight/get-seats-reserved-on-flight/{airline}/{flightNumber}", method = RequestMethod.GET)
    public ResponseEntity<List> getSeatsReservedOnFlight( @PathVariable("airline") String airline,
                                                          @PathVariable("flightNumber") int flightNumber) throws SQLException {

        List<String> customerAccts = flightService.getSeatsReservedOnFlight(airline, flightNumber);

        if (customerAccts != null)
            return new ResponseEntity<List>(customerAccts, HttpStatus.OK);
        return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);

    }

}
