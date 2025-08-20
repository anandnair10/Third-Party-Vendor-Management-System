package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.AdminDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;

public class AdminMapper {

    public static AdminDTO toDTO(Admin admin) {
        return AdminDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .role(admin.getRole())
                .passwordHash(admin.getPasswordHash())
                .adminCode(admin.getAdminCode())
                .department(admin.getDepartment())
                .build();
    }

    public static Admin toEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setFullName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setRole(dto.getRole());
        admin.setAdminCode(dto.getAdminCode());
        admin.setPasswordHash(dto.getPasswordHash());   
        admin.setDepartment(dto.getDepartment());
        return admin;
    }
}

