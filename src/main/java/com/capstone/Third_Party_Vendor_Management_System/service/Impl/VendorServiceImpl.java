package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired

    private PasswordEncoder passwordEncoder;
  
    private RatingRepository ratingRepository;

    @Override
    public Vendor registerVendor(Vendor vendor) {
        String hashedPassword = passwordEncoder.encode(vendor.getPasswordHash());
        vendor.setPasswordHash(hashedPassword);
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
    public Vendor updateVendor(Long vendorId, Vendor vendor) {
        Vendor existingVendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id " + vendorId));

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

        return vendorRepository.save(existingVendor);
    }

    @Override
    public Vendor deleteVendor(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .map(vendor -> {
                    vendorRepository.delete(vendor);
                    return vendor;
                })
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + vendorId));
    }

    @Override
    public List<TopRatedVendorDTO> getVendorsSortedByRatingDesc() {
        List<Vendor> vendors = vendorRepository.findAll();

        return vendors.stream()
                .map(vendor -> {
                    List<Rating> ratings = vendor.getRatings();
                    double avgRating = ratings.isEmpty() ? 0.0 :
                            ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
                    return new TopRatedVendorDTO(vendor.getId(), vendor.getBusinessName(), avgRating);
                })
                .sorted(Comparator.comparingDouble(TopRatedVendorDTO::getAverageRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingVendor(Long vendorId) {
        List<Rating> ratings = ratingRepository.findByVendorId(vendorId);
        return ratings.isEmpty() ? null :
                ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
    }
}
