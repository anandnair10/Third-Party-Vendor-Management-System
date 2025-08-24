package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.RiskLevel;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Vendor registerVendor(Vendor vendor) {
        logger.info("Registering new vendor: {}", vendor.getBusinessName());
        String hashedPassword = passwordEncoder.encode(vendor.getPasswordHash());
        vendor.setPasswordHash(hashedPassword);
        Vendor savedVendor = vendorRepository.save(vendor);
        logger.info("Vendor registered with ID: {}", savedVendor.getId());
        return savedVendor;
    }

    @Override
    public Vendor getVendorById(Long vendorId) {
        logger.info("Fetching vendor with ID: {}", vendorId);
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> {
                    logger.error("Vendor not found with ID: {}", vendorId);
                    return new RuntimeException("Vendor not found with id " + vendorId);
                });
    }

    @Override
    public Page<Vendor> getAllVendors(Pageable pageable) {
        logger.info("Fetching all vendors");
        return vendorRepository.findAll(pageable);
    }

    @Override
    public Vendor updateVendor(Long vendorId, Vendor vendor) {
        logger.info("Updating vendor with ID: {}", vendorId);
        Vendor existingVendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> {
                    logger.error("Vendor not found with ID: {}", vendorId);
                    return new EntityNotFoundException("Vendor not found with id " + vendorId);
                });

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

        Vendor updatedVendor = vendorRepository.save(existingVendor);
        logger.info("Vendor updated with ID: {}", updatedVendor.getId());
        return updatedVendor;
    }

    @Override
    public Vendor deleteVendor(Long vendorId) {
        logger.info("Deleting vendor with ID: {}", vendorId);
        return vendorRepository.findById(vendorId)
                .map(vendor -> {
                    vendorRepository.delete(vendor);
                    logger.info("Vendor deleted with ID: {}", vendorId);
                    return vendor;
                })
                .orElseThrow(() -> {
                    logger.error("Vendor not found with ID: {}", vendorId);
                    return new EntityNotFoundException("Vendor not found with ID: " + vendorId);
                });
    }

    @Override
    public List<TopRatedVendorDTO> getVendorsSortedByRatingDesc() {
        logger.info("Fetching vendors sorted by average rating");
        List<Vendor> vendors = vendorRepository.findAll();

        List<TopRatedVendorDTO> sortedVendors = vendors.stream()
                .map(vendor -> {
                    List<Rating> ratings = vendor.getRatings();
                    double avgRating = ratings.isEmpty() ? 0.0 :
                            ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
                    return new TopRatedVendorDTO(vendor.getId(), vendor.getBusinessName(), avgRating);
                })
                .sorted(Comparator.comparingDouble(TopRatedVendorDTO::getAverageRating).reversed())
                .collect(Collectors.toList());

        logger.debug("Sorted {} vendors by rating", sortedVendors.size());
        return sortedVendors;
    }

    @Override
    public Double getAverageRatingVendor(Long vendorId) {
        logger.info("Calculating average rating for vendor ID: {}", vendorId);
        List<Rating> ratings = ratingRepository.findByVendorId(vendorId);
        Double average = ratings.isEmpty() ? null :
                ratings.stream().mapToInt(Rating::getRatingValue).average().orElse(0.0);
        logger.debug("Average rating for vendor ID {}: {}", vendorId, average);
        return average;
    }

    // Determine risk level based on average rating
    public RiskLevel calculateRiskLevel(Double rating) {
        if (rating == null) return RiskLevel.HIGH;
        if (rating <= 2.5) return RiskLevel.HIGH;
        else if (rating <= 3.8) return RiskLevel.MEDIUM;
        else return RiskLevel.LOW;
    }

    @Override
    public List<Vendor> getAllVendor() {
        return vendorRepository.findAll();
    }


}
