package com.expressway.service.impl;

import com.expressway.model.Customer;
import com.expressway.model.User;
import com.expressway.service.CustomerService;
import com.expressway.util.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {


    Connection connection = ConnectionUtil.getConnection();


    @Override
    public Map validateUser(User user) {

        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, "user");
            ResultSet rs = statement.executeQuery();

            Map custMap = new HashMap();

            System.out.println(rs.getFetchSize());

            while (rs.next()){
                custMap.put("role", rs.getString(1));
                custMap.put("id", rs.getString(2));
            }

            return custMap;


        } catch (SQLException e) {

            e.printStackTrace();
            // if no match return null;
            return null;

        }

    }

    @Override
    public boolean addUser(Customer user) {


        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT LAST_INSERT_ID() FROM Person LIMIT 1";
        String userQuery = "INSERT INTO User (username, password, role, person_id) VALUE (?, ?, ?, ?);";
        String customerQuery = "INSERT INTO Customer (id, account_number, username, credit_card, telephone, email, rating) VALUE (?, ?, ?, ?, ?, ?, ?)";

        try {

            int i = 1;

            PreparedStatement personStatement = connection.prepareStatement(personQuery);
            personStatement.setString(i++, user.getFirstname());
            personStatement.setString(i++, user.getLastname());
            personStatement.setString(i++, user.getAddress());
            personStatement.setString(i++, user.getCity());
            personStatement.setString(i++, user.getState());
            personStatement.setString(i++, user.getZipcode());

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
            userStatement.setString(i++,user.getUsername());
            userStatement.setString(i++,user.getPassword());
            userStatement.setString(i++,"user");
            userStatement.setInt(i++,lastInsertedId);

            userStatement.executeUpdate();

            i = 1;
            PreparedStatement employeeStatement = connection.prepareStatement(customerQuery);
            employeeStatement.setInt(i++, lastInsertedId);
            employeeStatement.setString(i++, user.getUsername() + user.getZipcode());
            employeeStatement.setString(i++, user.getUsername());
            employeeStatement.setString(i++, user.getCreditcard());
            employeeStatement.setString(i++, user.getTelephone());
            employeeStatement.setString(i++, user.getEmail());
            employeeStatement.setInt(i++, 10);

            employeeStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }

        return true;

    }

    @Override
    public boolean deleteUser(int personId) {

        List<String> queryList = new ArrayList<>();
        queryList.add("DELETE FROM Customer WHERE Customer.id = ?");
        queryList.add("DELETE FROM User WHERE User.person_id = ?");
        queryList.add("DELETE FROM Person WHERE Person.id = ?");

        try {

            for (String query : queryList) {
                PreparedStatement curQuery = connection.prepareStatement(query);
                curQuery.setInt(1, personId);
                curQuery.executeUpdate();
            }
            return true;

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return false;
    }


    @Override
    public boolean updateUser(User user) {
        return false;
    }

}
