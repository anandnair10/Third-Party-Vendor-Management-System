package com.capstone.Third_Party_Vendor_Management_System.dto;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String businessName;
    private String businessStructure;
    private Integer establishedYear;
    private String primaryContactName;
    private String primaryContactDesignation;
    private String emailAddress;
    private String website;
    private String companyAddress;
    private String vendorType;
    private String description;
    private String pricing;
}
