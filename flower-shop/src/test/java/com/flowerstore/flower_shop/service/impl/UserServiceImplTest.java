package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.constants.UserType;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
        testUser = new User(1L, "Test User", "test@example.com", "password123", "0123456789", "Test Address", UserType.CLIENT);
    }

    @Test
    void givenValidUser_whenAddUser_thenReturnSavedUser() {
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.addUser(testUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void whenGetAllUsers_thenReturnUserList() {
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenExistingUser_whenGetUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(testUser);

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistingUser_whenGetUserById_thenThrowException() {
        when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));
    }

    @Test
    void givenValidUser_whenUpdateUser_thenReturnUpdatedUser() {
        User updatedUser = new User(1L, "Updated Name", "updated@example.com", "newpassword", "9876543210", "New Address", UserType.ADMIN);
        when(userRepository.updateUser(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).updateUser(updatedUser);
    }
}
