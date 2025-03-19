package com.flowerstore.flower_shop.service;

import com.flowerstore.flower_shop.model.User;

import java.util.List;


public interface IUserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(User user);
    void deleteUser(Long id);
}
