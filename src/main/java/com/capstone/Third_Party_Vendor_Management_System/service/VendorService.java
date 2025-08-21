package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;

import java.util.List;

public interface VendorService {

    Vendor registerVendor(Vendor vendor);
    Vendor getVendorById(Long vendorId);
    List<Vendor> getAllVendors();
    Vendor updateVendor(Long VendorId, Vendor vendor);
    Vendor deleteVendor(Long VendorId);

    List<TopRatedVendorDTO> getVendorsSortedByRatingDesc();
    Double getAverageRatingVendor(Long vendorId);


}
