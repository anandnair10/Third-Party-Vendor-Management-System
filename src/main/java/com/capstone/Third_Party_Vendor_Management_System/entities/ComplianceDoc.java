package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class ComplianceDoc {
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "verification_status")
    private VerificationStatus verificationStatus;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

}
