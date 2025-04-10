package com.loginservice.login_service.service.impl;

import com.loginservice.login_service.dto.AuthDTO;
import com.loginservice.login_service.model.User;
import com.loginservice.login_service.repository.UserRepository;
import com.loginservice.login_service.service.IAuthService;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(AuthDTO auth) {
        User user = userRepository.findByUsernameAndPassword(auth.getUsername(), auth.getPassword());
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        return user;
    }
}
