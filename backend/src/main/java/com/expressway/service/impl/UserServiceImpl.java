package com.expressway.service.impl;

import com.expressway.model.SignUp;
import com.expressway.model.User;
import com.expressway.util.Helper;
import com.expressway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Map validateUser(User user) {

        String query = "SELECT role FROM User WHERE User.username = ? AND User.password = ? ";

        try {
            // if match, return role of the user;

            return jdbcTemplate.queryForMap(query, new Object[]{user.getUsername(), user.getPassword()});

        } catch (EmptyResultDataAccessException e) {

            // if no match return null;
            return null;

        }

    }

    @Override
    public boolean addUser(SignUp user) {

        System.out.println(user.toString());

        String personQuery = "INSERT INTO Person (first_name, last_name, address, city, state, zip_code) VALUE (?, ?, ? , ? , ? , ? )";
        String last_id = "SELECT last_insert_id() from Person";
        String userQuery = "INSERT INTO User (username, password, role) VALUE (?, ?, ?);";
        String customerQuery = "INSERT INTO Customer (id, account_number, username, credit_card, telephone, email, rating) VALUE (?, ?, ?, ?, ?, ?, ?)";


        try {

            jdbcTemplate.update(personQuery, new Object[]{user.getFirstname(), user.getLastname(), user.getAddress(), user.getCity(), user.getState(), user.getZipcode()});
            List id = jdbcTemplate.queryForList(last_id);
            Integer convertedId = Helper.integerId(id.get(0).toString());

            jdbcTemplate.update(userQuery, new Object[]{user.getUsername(), user.getPassword(), "user"});
            jdbcTemplate.update(customerQuery, new Object[]{convertedId, user.getUsername() + user.getZipcode(), user.getUsername(),
                    user.getCreditcard(), user.getTelephone(), user.getEmail(), new Integer(0)});

        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;

    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

}
