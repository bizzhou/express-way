package com.expressway.service;

import com.expressway.model.User;

public interface UserService {

    String validateUser(User user);

    boolean addUser(User user);

    boolean deleteUser(User user);

    boolean updateUser(User user);

}
