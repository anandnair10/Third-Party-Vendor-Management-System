package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopRatedVendorDTO {
    private Long vendorId;
    private String businessName;
    private Double averageRating;

    public TopRatedVendorDTO() {
        // No-args constructor required by frameworks like Jackson
    }

    public TopRatedVendorDTO(Long vendorId, String businessName, Double averageRating) {
        this.vendorId = vendorId;
        this.businessName = businessName;
        this.averageRating = averageRating;
    }
}
