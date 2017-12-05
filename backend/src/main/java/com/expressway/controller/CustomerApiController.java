package com.expressway.controller;

import com.expressway.model.Auction;
import com.expressway.model.Include;
import com.expressway.model.Reservation;
import com.expressway.model.ReservationContext;
import com.expressway.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
     *
     * @param account
     * @param resvNumber
     * @return sample access: http://localhost:8080/1000003/reservations/itinerary?reservation-number=127
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

    @RequestMapping(value = "/{customerAccount}/reservations/history", method = RequestMethod.GET)
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


    @RequestMapping(value = "one-way-resv", method = RequestMethod.POST)
    public ResponseEntity<Map> makeOneWayResv(@RequestBody final ReservationContext reservationContext) throws IOException {

        Integer result;
        System.out.println(reservationContext);

        if ((result = customerService.oneWayResv(reservationContext)) != null) {
            return new ResponseEntity<Map>(customerService.getReservationDetails(result), HttpStatus.OK);
        }

        return new ResponseEntity<Map>(HttpStatus.BAD_REQUEST);
    }


//    @RequestMapping(value = "two-way-resv", method = RequestMethod.POST)
//    public ResponseEntity<Map> makeOneWayResv(@RequestBody final List<Reservation> reservations) throws IOException {
//
//        Map result;
//        if ((result = customerService.twoWayResv(reservations)) != null) {
//            return new ResponseEntity<Map>(result, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<Map>(HttpStatus.BAD_REQUEST);
//    }

    @RequestMapping(value = "reverse-bid", method = RequestMethod.POST)
    public ResponseEntity<Boolean> makeReverseBid(@RequestBody final Auction auction) throws IOException {


        if (customerService.reverseBid(auction) == true) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "user/{customerAccount}/bids", method = RequestMethod.GET)
    public ResponseEntity<List> getUserBids(@PathVariable("customerAccount") String account) {

        List res = customerService.getBids(account);

        if (res != null) {
            return new ResponseEntity<List>(res, HttpStatus.OK);
        }

        return new ResponseEntity<List>(res, HttpStatus.BAD_REQUEST);


    }

    @RequestMapping(value = "user/bid-reservation", method = RequestMethod.POST)
    public ResponseEntity<Boolean> bidReservation(@RequestBody final Include inc) throws IOException {

        if (customerService.insertInclude(inc)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "user/reservation/delete/{reservationNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> bidReservation(@PathVariable int reservationNumber) throws IOException {

        if (customerService.cancelReservation(reservationNumber)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

        return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
    }



}
