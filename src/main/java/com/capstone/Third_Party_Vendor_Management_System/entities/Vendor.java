package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class Vendor extends User{

    private String vendorName; // Business name

    private String businessStructure; // Sole proprietorship, partnership, etc.

    private String established; // Year established

    private String primaryContactName;

    private String primaryContactDesignation;

    private String website;

    private String companyAddress;

    private String serviceType; // e.g. IT Services, Catering, etc.

    private String description;

    private String pricing; // Could be numeric if needed

}
