package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.User;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import com.capstone.Third_Party_Vendor_Management_System.repository.UserRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPhoneNumber("1234567890");
        user.setPasswordHash("hashedPassword");
        user.setRole(Role.ADMIN);
    }

    @Test
    void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertEquals(user, savedUser);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setFullName("Jane Doe");
        updatedUser.setEmail("jane@example.com");
        updatedUser.setPhoneNumber("0987654321");
        updatedUser.setPasswordHash("newHash");
        updatedUser.setRole(Role.VENDOR);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);
        assertEquals("Jane Doe", result.getFullName());
        assertEquals("jane@example.com", result.getEmail());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}

