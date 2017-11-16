package com.expressway.service;

import java.util.List;
import java.util.Map;

public interface ManagerLevelService {

    Integer getEmployeeWithMostRevenue();

    String getCustomerWithMostSpent();

    List<Map<String,Object>> getRevenueByFlight(String airline, int flightNumber);

    List<Map<String,Object>> getRevenueByCity(String destinationCity);

    List<Map<String,Object>> getRevenueByCustomer(String customerAcct);

    List<Map<String,Object>> getReservationByFlight(String airline, int flightNumber);

    List<Map<String,Object>> getReservationByCustomerName(String customerName);

    Double getMonthlySalesReport(String startDate, String endDate);

    List<String> getCustomerEmails();
}
