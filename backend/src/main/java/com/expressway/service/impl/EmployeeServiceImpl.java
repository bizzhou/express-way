package com.expressway.service.impl;

import com.expressway.model.Employee;
import com.expressway.model.User;
import com.expressway.service.EmployeeService;
import com.expressway.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private ConnectionUtil connectionUtil;

    Connection connection = ConnectionUtil.getConnection();

    @Override
    public Map validateEmployee(User user) {

        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        try{

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, "employee");
            ResultSet rs = statement.executeQuery();

            Map empMap = new HashMap();

            System.out.println(rs.getFetchSize());

            while (rs.next()){
                empMap.put("role", rs.getString(1));
                empMap.put("id", rs.getString(2));
            }

            return empMap;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addEmployee(Employee emp) {

        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
        String employeeQuery = "INSERT INTO Employee (ssn, id, username, is_manager, hourly_rate, telephone) VALUE (?, ?, ?, ?, ?, ?)";

        try {

            int i = 1;

            PreparedStatement personStatement = connection.prepareStatement(personQuery);
            personStatement.setString(i++, emp.getFirst_name());
            personStatement.setString(i++, emp.getLast_name());
            personStatement.setString(i++, emp.getAddress());
            personStatement.setString(i++, emp.getCity());
            personStatement.setString(i++, emp.getState());
            personStatement.setInt(i++, emp.getZip_code());

            personStatement.executeUpdate();

            i = 1;
            PreparedStatement idStatement = connection.prepareStatement(last_id);
            ResultSet resultSet = idStatement.executeQuery();

            int lastInsertedId = 0;
            while (resultSet.next()){
                lastInsertedId = resultSet.getInt(1);
            }

            if(lastInsertedId == 0){
                System.out.printf("Cannot get the last inserted id");
                return false;
            }

            PreparedStatement userStatement = connection.prepareStatement(userQuery);
            userStatement.setString(i++,emp.getUsername());
            userStatement.setString(i++,emp.getPassword());
            userStatement.setString(i++,"employee");
            userStatement.setInt(i++,lastInsertedId);

            userStatement.executeUpdate();

            i = 1;
            PreparedStatement employeeStatement = connection.prepareStatement(employeeQuery);
            employeeStatement.setString(i++,emp.getSsn());
            employeeStatement.setInt( i++, lastInsertedId);
            employeeStatement.setString(i++, emp.getUsername());
            employeeStatement.setBoolean(i++, emp.isManger());
            employeeStatement.setDouble(i++, emp.getHourly_rate());
            employeeStatement.setString(i++, emp.getTelephone());

            employeeStatement.executeUpdate();

            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteEmployee(int id) {

        List<String> queryList = new ArrayList<>();
        queryList.add("DELETE FROM Employee WHERE Employee.id = ?");
        queryList.add("DELETE FROM User WHERE User.person_id = ?");
        queryList.add("DELETE FROM Person WHERE Person.id = ?");

        try {


            for (String query : queryList) {
                PreparedStatement curQuery = connection.prepareStatement(query);
                curQuery.setInt(1, id);
                curQuery.executeUpdate();
            }

            System.out.println("Deletion Complete " + id);

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean updateEmployee(Employee user) {
        return false;
    }
}
