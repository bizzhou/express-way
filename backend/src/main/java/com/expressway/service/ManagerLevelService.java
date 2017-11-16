package com.expressway.service;

import java.util.List;
import java.util.Map;

public interface ManagerLevelService {

    Integer getEmployeeWithMostRevenue();

    String getCustomerWithMostSpent();

    List<Map<String,Object>> getRevenueByFlight(String airline, int flightNumber);

    List<Map<String,Object>> getRevenueByCity(String destinationCity);
}
