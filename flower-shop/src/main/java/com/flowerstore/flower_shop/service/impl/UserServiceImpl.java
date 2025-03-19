package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.UserRepository;
import com.flowerstore.flower_shop.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id);
        if(user != null) {
            return user;
        }
        throw new NoSuchElementException("User with id" +  id + " does not exist");
    }

    @Override
    public User updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
