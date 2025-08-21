package com.capstone.Third_Party_Vendor_Management_System.dto;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
    private String fullName;
    private Role role;
    private String businessName;
    private Integer establishedYear;
    private String companyAddress;
    private VendorType vendorType;
}
