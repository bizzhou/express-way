package com.expressway.service;

import com.expressway.model.FlightSearch;
import com.expressway.model.Flight;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FlightService {

    List<Map<String, Object>> serachFlight(FlightSearch flight);

    List<Map<String, Object>> getMostFreqFlights();

    List<Map<String, Object>> getFlightsForAirport(String airportId);

}
