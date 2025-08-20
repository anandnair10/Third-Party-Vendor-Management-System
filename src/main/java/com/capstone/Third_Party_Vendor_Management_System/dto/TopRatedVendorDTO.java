package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopRatedVendorDTO {
    private Long vendorId;
    private String businessName;
    private Double averageRating;
}
