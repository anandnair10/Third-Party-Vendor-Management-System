package com.capstone.Third_Party_Vendor_Management_System.mapper;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;

import java.time.LocalDate;

public class RatingMapper {

    public static Rating toEntity(RatingDTO dto, Employee employee, Vendor vendor) {
        return Rating.builder()
                .employee(employee)
                .vendor(vendor)
                .ratingValue(dto.getRatingValue())
                .feedback(dto.getFeedback())
                .ratingDate(LocalDate.now())
                .build();
    }

    public static RatingDTO toDTO(Rating rating) {
        RatingDTO dto = new RatingDTO();
        dto.setRatingValue(rating.getRatingValue());
        dto.setFeedback(rating.getFeedback());
        dto.setEmployeeId(rating.getEmployee().getId());
        dto.setVendorId(rating.getVendor().getId());

        return dto;
    }
}
