package com.capstone.Third_Party_Vendor_Management_System.dto;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.RiskLevel;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRiskDTO {
    private Long vendorId;
    private String vendorName;
    private double rating;
    private RiskLevel riskLevel;

    public VendorRiskDTO(Long vendorId, String vendorName, Double averageRating, RiskLevel riskLevel) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.rating = averageRating;
        this.riskLevel = riskLevel;
    }
}
