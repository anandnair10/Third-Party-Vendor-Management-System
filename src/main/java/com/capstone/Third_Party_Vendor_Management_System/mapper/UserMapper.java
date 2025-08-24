package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.UserDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPasswordHash());
        user.setRole(dto.getRole());
        return user;
    }
}
