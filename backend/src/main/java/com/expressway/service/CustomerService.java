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

    List getUsers();

    List<Map<String,Object>> getCustomerReservations(String customerAccount);
}
