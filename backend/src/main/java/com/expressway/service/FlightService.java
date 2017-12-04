package com.expressway.service;

import com.expressway.model.FlightSearch;
import com.expressway.model.Flight;
import com.expressway.model.Leg;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FlightService {

    List<Map<String, Object>> getMostFreqFlights();

    List<Map<String, Object>> getFlightsForAirport(String airportId);

    List<String> getSeatsReservedOnFlight(String airline, int flightNumber);

    List getAllLegs();

    ArrayList<Leg> getLegsByAirport(String fromAirport, String toAirport);

    List<Object> getAllFlights();

    ArrayList<ArrayList<Leg>> getFareInformation(ArrayList<ArrayList<Leg>> routes, FlightSearch fs);


}
