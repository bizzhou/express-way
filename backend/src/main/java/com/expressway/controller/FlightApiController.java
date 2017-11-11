package com.expressway.controller;


import com.expressway.jwt.JwtUtil;
import com.expressway.model.FlightSearch;
import com.expressway.model.User;
import com.expressway.service.impl.FlightServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FlightApiController {


    @Autowired
    private FlightServiceImpl flightService;

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(FlightApiController.class);


    @RequestMapping(value = "/flight/search", method = RequestMethod.POST)
    public ResponseEntity<Map> login(@RequestBody final FlightSearch flightSearch) throws IOException {

        Map result;

        logger.info("********************************************************************************");


        if ((result = flightService.serachFlight(flightSearch)) != null) {

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }





}
