package com.flowerstore.flower_shop.service.impl;

import com.flowerstore.flower_shop.constants.UserType;
import com.flowerstore.flower_shop.exceptions.UserAlreadyExistsException;
import com.flowerstore.flower_shop.model.User;
import com.flowerstore.flower_shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ✅ OBLIGATORIU pentru mock-uri injectate automat
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .password("password123")
                .phoneNumber("0123456789")
                .address("Test Address")
                .role(UserType.CLIENT)
                .build();
    }

    @Test
    void givenValidUser_whenAddUser_thenReturnSavedUser() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.addUser(testUser);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).existsByEmail(testUser.getEmail());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void givenExistingEmail_whenAddUser_thenThrowException() {
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(testUser));

        verify(userRepository, times(1)).existsByEmail(testUser.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    void whenGetAllUsers_thenReturnUserList() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenExistingUser_whenGetUserById_thenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void givenNonExistingUser_whenGetUserById_thenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void givenValidUser_whenUpdateUser_thenReturnUpdatedUser() {
        when(userRepository.existsById(testUser.getId())).thenReturn(true);
        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.updateUser(testUser);

        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(userRepository, times(1)).existsById(testUser.getId());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void givenNonExistingUser_whenUpdateUser_thenThrowException() {
        when(userRepository.existsById(testUser.getId())).thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> userService.updateUser(testUser));

        verify(userRepository, times(1)).existsById(testUser.getId());
        verify(userRepository, never()).save(any());
    }

    @Test
    void givenUserId_whenDeleteUser_thenInvokeDelete() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}
