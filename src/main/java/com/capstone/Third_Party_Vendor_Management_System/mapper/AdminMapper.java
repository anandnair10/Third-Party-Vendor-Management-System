package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.AdminDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;

public class AdminMapper {

    public static AdminDTO toDTO(Admin admin) {
        return AdminDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .role(admin.getRole())
                .department(admin.getDepartment())
                .build();
    }

    public static Admin toEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setFullName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        admin.setRole(dto.getRole());
        admin.setDepartment(dto.getDepartment());
        return admin;
    }
}

