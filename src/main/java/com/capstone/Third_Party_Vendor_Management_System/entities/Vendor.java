package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendors")
public class Vendor extends User{

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_structure") // e.g., Sole Proprietorship, Partnership, Pvt Ltd, etc.
    private String businessStructure;

    @Column(name = "established_year")
    private Integer establishedYear;

    @Column(name = "primary_contact_name", nullable = false)
    private String primaryContactName;

    @Column(name = "primary_contact_designation")
    private String primaryContactDesignation;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "website")
    private String website;

    @Column(name = "company_address", columnDefinition = "TEXT")
    private String companyAddress;

    @Column(name = "vendor_type") // e.g., Catering, Photography, IT Services
    private String vendorType;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

//    @Column(name = "role") // Role in the system or contract
//    private String vendorrole;

    @Column(name = "pricing", columnDefinition = "TEXT")
    private String pricing; // Can be structured later (JSON, table, etc.)

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;
}
