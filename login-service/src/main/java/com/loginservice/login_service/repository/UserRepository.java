package com.loginservice.login_service.repository;

import com.loginservice.login_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public User findByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    public void saveAll(List<User> nwUsers) {
        users.addAll(nwUsers);
    }
    public List<User> findAll() {
        return users;
    }
}
