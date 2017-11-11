package com.expressway.service.impl;

import com.expressway.model.FlightSearch;
import com.expressway.service.FlightService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FlightServiceImpl implements FlightService{

    @Override
    public Map serachFlight(FlightSearch flight) {

        System.out.println(flight);
        return null;

    }


}
