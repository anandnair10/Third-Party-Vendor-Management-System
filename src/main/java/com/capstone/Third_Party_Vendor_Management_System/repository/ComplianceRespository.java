package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceRespository extends JpaRepository<Compliance,Long> {
    List<Compliance> findByVendorId(Long vendorId);
}
