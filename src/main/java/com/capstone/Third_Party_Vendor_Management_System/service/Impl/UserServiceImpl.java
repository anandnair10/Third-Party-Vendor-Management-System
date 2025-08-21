package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.User;
import com.capstone.Third_Party_Vendor_Management_System.repository.UserRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        logger.info("Saving new user with email: {}", user.getEmail());
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        User savedUser = userRepository.save(user);
        logger.info("User saved with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        logger.info("Updating user with ID: {}", id);
        return userRepository.findById(id).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            String hashedPassword = passwordEncoder.encode(updatedUser.getPasswordHash());
            user.setPasswordHash(hashedPassword);
            user.setRole(updatedUser.getRole());
            User savedUser = userRepository.save(user);
            logger.info("User updated with ID: {}", savedUser.getId());
            return savedUser;
        }).orElseThrow(() -> {
            logger.error("User not found with ID: {}", id);
            return new RuntimeException("User not found with id " + id);
        });
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
        logger.info("User deleted with ID: {}", id);
    }



}
