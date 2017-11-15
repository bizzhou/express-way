package com.expressway.service;

import com.expressway.model.Employee;
import com.expressway.model.User;

import java.util.Map;

public interface EmployeeService {

    Map validateEmployee(User user);

    boolean addEmployee(Employee Signup);

    boolean deleteEmployee(int id);

    boolean updateEmployee(Employee user);

    Integer getEmployeeWithMostRevenue();

}
