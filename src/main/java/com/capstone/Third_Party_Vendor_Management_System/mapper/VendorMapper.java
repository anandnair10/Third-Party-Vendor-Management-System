package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.EmployeeDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;

public class VendorMapper {

    public static VendorDTO toDTO(Vendor vendor) {
        return VendorDTO.builder()
                .id(vendor.getId())
                .fullName(vendor.getFullName())
                .role(vendor.getRole())
                .businessName(vendor.getBusinessName())
                .establishedYear(vendor.getEstablishedYear())
                .companyAddress(vendor.getCompanyAddress())
                .vendorType(vendor.getVendorType())
                .build();
    }
    public static Vendor toEntity(VendorDTO dto) {
        Vendor vendor = new Vendor();
        vendor.setId(dto.getId()); // optional for updates
        vendor.setFullName(dto.getFullName());
        vendor.setRole(dto.getRole());
        vendor.setBusinessName(dto.getBusinessName());
        vendor.setEstablishedYear(dto.getEstablishedYear());
        vendor.setCompanyAddress(dto.getCompanyAddress());
        vendor.setVendorType(dto.getVendorType());
        return vendor;
    }
}
