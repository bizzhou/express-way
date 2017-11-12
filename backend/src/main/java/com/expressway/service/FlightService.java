package com.expressway.service;

import com.expressway.model.FlightSearch;

import java.util.List;
import java.util.Map;

public interface FlightService {

    List<Map<String, Object>> serachFlight(FlightSearch flight);

}
