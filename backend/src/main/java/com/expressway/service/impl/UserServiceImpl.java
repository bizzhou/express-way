package com.expressway.service.impl;

import com.expressway.model.User;
import com.expressway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String validateUser(User user) {

        String query = "SELECT role FROM User WHERE User.username = ? AND User.password = ? ";

        try {
            // if match, return role of the user;

            return jdbcTemplate.queryForObject(query, new Object[]{user.getUsername(), user.getPassword()}, String.class);

        } catch (EmptyResultDataAccessException e) {

            // if no match return null;
            return null;

        }

    }

    @Override
    public boolean addUser(User user) {
        return false;
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
