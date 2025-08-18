package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id " + vendorId));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor updateVendor(Long VendorId, Vendor vendor) {
        Vendor existingVendor = vendorRepository.findById(VendorId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id " + VendorId));

        existingVendor.setFullName(vendor.getFullName());
        existingVendor.setBusinessName(vendor.getBusinessName());
        existingVendor.setBusinessStructure(vendor.getBusinessStructure());
        existingVendor.setEstablishedYear(vendor.getEstablishedYear());
        existingVendor.setPrimaryContactDesignation(vendor.getPrimaryContactDesignation());
        existingVendor.setPrimaryContactName(vendor.getPrimaryContactName());
        existingVendor.setPhoneNumber(vendor.getPhoneNumber());
        existingVendor.setEmailAddress(vendor.getEmailAddress());
        existingVendor.setWebsite(vendor.getWebsite());
        existingVendor.setCompanyAddress(vendor.getCompanyAddress());
        existingVendor.setVendorType(vendor.getVendorType());
        existingVendor.setDescription(vendor.getDescription());
        existingVendor.setPricing(vendor.getPricing());

        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor deleteVendor(Long VendorId) {


        return vendorRepository.findById(VendorId)
                .map(vendor -> {
                    vendorRepository.delete(vendor);
                    return vendor;
                })
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + VendorId));
    }
}
