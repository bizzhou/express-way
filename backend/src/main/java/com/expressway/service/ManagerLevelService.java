package com.expressway.service;

import com.expressway.model.Auction;

import java.util.List;
import java.util.Map;

public interface ManagerLevelService {

    Map getEmployeeWithMostRevenue();

    Map getCustomerWithMostSpent();

    List<Map<String, Object>> getRevenueByFlight(String airline, int flightNumber);

    List<Map<String, Object>> getRevenueByCity(String destinationCity);

    List<Map<String, Object>> getRevenueByCustomer(String customerAcct);

    List<Map<String, Object>> getReservationByFlight(String airline, int flightNumber);

    List<Map<String, Object>> getReservationByCustomerName(String customerName);

    Double getMonthlySalesReport(String startDate, String endDate);

    List getBids();

    boolean auctionToReservation(Auction auction);
}
