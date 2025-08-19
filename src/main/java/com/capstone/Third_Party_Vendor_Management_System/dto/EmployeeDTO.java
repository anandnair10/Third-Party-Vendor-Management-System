package com.capstone.Third_Party_Vendor_Management_System.dto;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String department;
    private String username;
}
