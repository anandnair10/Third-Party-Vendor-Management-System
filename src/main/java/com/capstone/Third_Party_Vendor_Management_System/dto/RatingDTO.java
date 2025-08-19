package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    private Long ratingId;
    private Long employeeId;
    private Integer ratingValue;
    private String feedback;
    private LocalDate ratingDate;
    private Long vendorId;
}
