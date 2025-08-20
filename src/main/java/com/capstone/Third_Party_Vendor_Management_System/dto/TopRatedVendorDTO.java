package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TopRatedVendorDTO {
    private Long vendorId;
    private String businessName;
    private Double averageRating;
}
