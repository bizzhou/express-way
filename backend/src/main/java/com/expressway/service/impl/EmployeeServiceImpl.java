package com.expressway.service.impl;

import com.expressway.model.Employee;
import com.expressway.model.User;
import com.expressway.service.EmployeeService;
import com.expressway.util.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    Connection connection = ConnectionUtil.getConnection();

    @Override
    public Map validateEmployee(User user) {
        return null;
    }

    @Override
    public boolean addEmployee(Employee Signup) {
        return false;
    }

    @Override
    public boolean deleteEmployee(int id) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee user) {
        return false;
    }

//    @Override
//    public Map validateUser(User user) {
//        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";
//        // TODO check if the employee is manager.
//
//        try {
//
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, "employee");
//
//
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return null;
//    }





}
