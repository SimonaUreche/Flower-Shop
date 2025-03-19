package com.flowerstore.flower_shop.repository;

import com.flowerstore.flower_shop.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class UserRepository {
    private final List <User> users = new ArrayList<>();
    private Long nextId = 1L; //momentan nu gestionam niste date din baza de date, deci simulam un autoincrement

    public User save(User user) {
        if(user.getId() == null) {
            user.setId(nextId++);
        }
        users.add(user);
        return user;
    }

    public void saveAll(List<User> newUsers) {
        users.addAll(newUsers);
    }
    public List<User> findAll() {
        return users;
    }

    public User findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User updateUser(User user) {
        User oldUser = findById(user.getId());

        if(oldUser != null) {
            oldUser.setName(user.getName());
            oldUser.setRole(user.getRole());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(user.getPassword());
            oldUser.setPhoneNumber(user.getPhoneNumber());
            oldUser.setAddress(user.getAddress());
            return oldUser;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
}
