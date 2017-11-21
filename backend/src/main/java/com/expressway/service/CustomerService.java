package com.expressway.service;

import com.expressway.model.Customer;
import com.expressway.model.User;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Map validateUser(User user);

    boolean addUser(Customer Signup);

    boolean deleteUser(int id);

    boolean updateUser(Customer user, int id);

    Map getUser(int id);

    List getUsers();

    List<Map<String, Object>> getCustomerReservations(String customerAccount);

    List<Map<String, Object>> getTravelItinerary(String account, int resvNumber);

    List<Map<String, Object>> getReservationHistory(String customerAccount);

    List<Map<String, Object>> getBestSellerFlights();

    List<Map<String, Object>> getPersonalizedFlights(String customerAccouisMangernt);
}
