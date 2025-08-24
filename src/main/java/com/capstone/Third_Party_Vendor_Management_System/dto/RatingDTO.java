package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
    private Long vendorId;
    private Long employeeId;
    private Integer ratingValue;
    private String feedback;

}
