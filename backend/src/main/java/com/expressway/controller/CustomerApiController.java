package com.expressway.controller;

import com.expressway.model.Customer;
import com.expressway.service.CustomerService;
import com.expressway.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CustomerApiController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/{customerAccount}/reservations", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> getCustomerReservations(@PathVariable("customerAccount") String customerAccount) {

        List<Map<String, Object>> reservations = customerService.getCustomerReservations(customerAccount);

        if (reservations != null)
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    /**
     * Get customer's travel itinerary by reservation number
     * @param account
     * @param resvNumber
     * @return
     * sample access: http://localhost:8080/1000003/reservations/itinerary?reservation-number=127
     * execute database/hw2.sql--"Make a multi-city reservation" if id=1000003 not found
     */
    @RequestMapping(value = "/{customerAccount}/reservations/itinerary", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> getTravelItinerary(@PathVariable("customerAccount") String account,
                                                                        @RequestParam("reservation-number") int resvNumber) {

        List<Map<String, Object>> itinerary = customerService.getTravelItinerary(account, resvNumber);

        if (itinerary != null)
            return new ResponseEntity<>(itinerary, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/{customerAccount}/reservations/history", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> getTravelItinerary(@PathVariable("customerAccount") String account) {

        List<Map<String, Object>> history = customerService.getReservationHistory(account);

        if (history != null)
            return new ResponseEntity<>(history, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{customerAccount}/flight/best-seller", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> getBestSellerFlights(@PathVariable("customerAccount") String account) {

        List<Map<String, Object>> bestSellers = customerService.getBestSellerFlights();

        if (bestSellers != null)
            return new ResponseEntity<>(bestSellers, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{customerAccount}/flight/personalized", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> getPersonalizedFlights(@PathVariable("customerAccount") String account) {

        List<Map<String, Object>> suggestions = customerService.getPersonalizedFlights(account);

        if (suggestions != null)
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
