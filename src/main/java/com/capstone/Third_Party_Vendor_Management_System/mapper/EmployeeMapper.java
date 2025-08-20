package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.EmployeeDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .role(employee.getRole())
                .department(employee.getDepartment())
                .build();
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId()); // optional for updates
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setRole(dto.getRole());
        employee.setDepartment(dto.getDepartment());
        return employee;
    }
}
