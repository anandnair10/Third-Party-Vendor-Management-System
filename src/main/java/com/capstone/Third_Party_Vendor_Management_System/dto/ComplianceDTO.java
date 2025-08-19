package com.capstone.Third_Party_Vendor_Management_System.dto;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VerificationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplianceDTO {
    private Long id;
    private String documentName;
    private String filePath;
    private LocalDateTime uploadedAt;
    private LocalDate uploadDate;
    private LocalDate expiryDate;
    private VerificationStatus verificationStatus;
    private Long vendorId;
}
