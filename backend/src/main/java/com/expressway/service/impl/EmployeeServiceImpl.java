package com.expressway.service.impl;

import com.expressway.model.Employee;
import com.expressway.model.User;
import com.expressway.service.EmployeeService;
import com.expressway.util.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean addEmployee(Employee emp) {

        System.out.println(emp.toString());
        System.out.println(emp.getZipcode());
        System.out.println(emp.getAddress());
        System.out.println(emp.getState());




//        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
//        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
//        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
//        String employeeQuery = "INSERT INTO Employee (ssn, id, username, is_manager, hourly_rate, telephone) VALUE (?, ?, ?, ?, ?, ?)";
//
//        try {
//
//            int i = 1;
//
//            PreparedStatement personStatement = connection.prepareStatement(personQuery);
//            personStatement.setString(i++, emp.getFirstname());
//            personStatement.setString(i++, emp.getLastname());
//            personStatement.setString(i++, emp.getAddress());
//            personStatement.setString(i++, emp.getCity());
//            personStatement.setString(i++, emp.getState());
//            personStatement.setString(i++, emp.getZipcode());
//
//            personStatement.executeUpdate();
//
//            i = 1;
//            PreparedStatement idStatement = connection.prepareStatement(last_id);
//            ResultSet resultSet = idStatement.executeQuery();
//
//            int lastInsertedId = 0;
//            while (resultSet.next()){
//                lastInsertedId = resultSet.getInt(1);
//            }
//
//            if(lastInsertedId == 0){
//                System.out.printf("Cannot get the last inserted id");
//                return false;
//            }
//
//            PreparedStatement userStatement = connection.prepareStatement(userQuery);
//            userStatement.setString(i++,emp.getUsername());
//            userStatement.setString(i++,emp.getPassword());
//            userStatement.setString(i++,"employee");
//            userStatement.setInt(i++,lastInsertedId);
//
//            userStatement.executeUpdate();
//
//            i = 1;
//            PreparedStatement employeeStatement = connection.prepareStatement(employeeQuery);
//            employeeStatement.setString(i++,emp.getSsn());
//            employeeStatement.setInt( i++, lastInsertedId);
//            employeeStatement.setString(i++, emp.getUsername());
//            employeeStatement.setBoolean(i++, emp.getManger());
//            employeeStatement.setDouble(i++, emp.getHourly_rate());
//            employeeStatement.setString(i++, emp.getTelephone());
//
//            employeeStatement.executeUpdate();
//
//            return true;
//
//        } catch (SQLException e){
//            return false;
//        }


        return true;

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
