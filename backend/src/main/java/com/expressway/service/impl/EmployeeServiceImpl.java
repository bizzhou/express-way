package com.expressway.service.impl;

import com.expressway.model.SignUp;
import com.expressway.model.User;
import com.expressway.service.UserService;
import com.expressway.util.ConnectionUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements UserService{

    Connection connection = ConnectionUtil.getConnection();

    @Override
    public Map validateUser(User user) {
        String query = "SELECT role, person_id, username FROM User WHERE User.username = ? AND User.password = ? AND User.role = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(query);



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addUser(SignUp form) {
        return false;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }
}
