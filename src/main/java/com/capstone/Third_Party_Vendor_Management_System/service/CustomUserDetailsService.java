package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.config.CustomUserDetails;
import com.capstone.Third_Party_Vendor_Management_System.entities.User;
import com.capstone.Third_Party_Vendor_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getRole() == null || (!user.getRole().name().equals("ADMIN") && !user.getRole().name().equals("EMPLOYEE") && !user.getRole().name().equals("VENDOR"))) {
            throw new UsernameNotFoundException("Access denied: Invalid role");
        }

        return new CustomUserDetails(user);
    }

}

