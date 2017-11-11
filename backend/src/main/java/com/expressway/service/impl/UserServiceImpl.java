package com.expressway.service.impl;

import com.expressway.model.Customer;
import com.expressway.model.User;
import com.expressway.service.CustomerService;
import com.expressway.util.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements CustomerService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    Connection connection = ConnectionUtil.getConnection();


    @Override
    public Map validateUser(User user) {

        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        try {
            // if match, return the map of role, perosn_id and username;

            return jdbcTemplate.queryForMap(query, new Object[]{user.getUsername(), user.getPassword(), "user"});

        } catch (EmptyResultDataAccessException e) {

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

            jdbcTemplate.update(personQuery, new Object[]{user.getFirstname(), user.getLastname(), user.getAddress(), user.getCity(), user.getState(), user.getZipcode()});
            Integer id = jdbcTemplate.queryForObject(last_id, Integer.class);

            jdbcTemplate.update(userQuery, new Object[]{user.getUsername(), user.getPassword(), "user", id});
            jdbcTemplate.update(customerQuery, new Object[]{id, user.getUsername() + user.getZipcode(), user.getUsername(),
                    user.getCreditcard(), user.getTelephone(), user.getEmail(), new Integer(0)});

        } catch (EmptyResultDataAccessException e) {
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
