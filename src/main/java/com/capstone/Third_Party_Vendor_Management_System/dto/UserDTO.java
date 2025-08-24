package com.capstone.Third_Party_Vendor_Management_System.dto;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String email;
    private String passwordHash;
    private Role role;
}
