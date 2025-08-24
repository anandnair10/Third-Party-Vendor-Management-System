package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorRiskDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.RiskLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendorService {

    Vendor registerVendor(Vendor vendor);
    Vendor getVendorById(Long vendorId);
    Page<Vendor> getAllVendors(Pageable pageable);
    Vendor updateVendor(Long VendorId, Vendor vendor);
    Vendor deleteVendor(Long VendorId);

    List<TopRatedVendorDTO> getVendorsSortedByRatingDesc();
    Double getAverageRatingVendor(Long vendorId);
    public RiskLevel calculateRiskLevel(Double rating);

    List<Vendor> getAllVendor();
}
