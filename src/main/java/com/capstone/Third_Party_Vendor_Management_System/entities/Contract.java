package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contractDetails;
    private String startDate;
    private String endDate;
    private Double contractValue;
    @OneToOne(mappedBy = "contract")
    private Vendor vendor;
}
