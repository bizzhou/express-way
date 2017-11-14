package com.expressway.service;

import com.expressway.model.Customer;
import com.expressway.model.User;

import java.util.Map;

public interface CustomerService {

    Map validateUser(User user);

    boolean addUser(Customer Signup);

    boolean deleteUser(int id);

    boolean updateUser(User user);

}
