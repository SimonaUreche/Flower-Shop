package com.loginservice.login_service.service.impl;

import com.loginservice.login_service.model.User;
import com.loginservice.login_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MockUserDataServiceImpl {
    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final UserRepository userRepository;

    public MockUserDataServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        generateMockUserData();
    }

    public void generateMockUserData() {
        List<User> users = new ArrayList<>();

        for(int i = 1; i <= 5; i++) {
            User user = new User();
            user.setId(random.nextLong(1, 100));
            user.setUsername(faker.internet().username());
            user.setPassword(faker.internet().password());
            users.add(user);
            System.out.println(user);
        }

        User user = new User();
        user.setId(101L);
        user.setUsername("test");
        user.setPassword("test");
        users.add(user);

        userRepository.saveAll(users);
    }

}
