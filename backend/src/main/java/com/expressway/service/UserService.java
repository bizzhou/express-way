package com.expressway.service;

import com.expressway.model.SignUp;
import com.expressway.model.User;

import java.util.Map;

public interface UserService {

    Map validateUser(User user);

    boolean addUser(SignUp form);

    boolean deleteUser(int id);

    boolean updateUser(User user);

}
