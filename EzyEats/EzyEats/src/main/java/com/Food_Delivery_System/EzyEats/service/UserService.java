package com.Food_Delivery_System.EzyEats.service;

import com.Food_Delivery_System.EzyEats.models.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User loginUser(String username, String password);

    User getUserById(int userId);

    User updateUser(int userId, User updatedUser);

    User findByUserName(String username);

    boolean deleteUser(int userId);

    List<User> getAllUsers();
}


