package com.expressway.service;

import com.expressway.model.Employee;
import com.expressway.model.User;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    Map validateEmployee(User user);

    boolean addEmployee(Employee Signup);

    boolean deleteEmployee(int id);

    List getAllEmployee();

    boolean updateEmployee(Employee user, int id);


    List<String> getCustomerEmails();

    List<Map<String,Object>> getFlightSuggestions(int customerId);
}
