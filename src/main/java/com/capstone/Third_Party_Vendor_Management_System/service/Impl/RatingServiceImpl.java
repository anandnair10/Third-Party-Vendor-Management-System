package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.mapper.RatingMapper;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingRepository ratingRepository;
    private final EmployeeRepository employeeRepository;
    private final VendorRepository vendorRepository;

    public RatingServiceImpl(RatingRepository ratingRepository,
                             EmployeeRepository employeeRepository,
                             VendorRepository vendorRepository) {
        this.ratingRepository = ratingRepository;
        this.employeeRepository = employeeRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Rating addRating(Rating rating) {
        logger.info("Adding rating for vendor ID: {}, employee ID: {}",
                rating.getVendor().getId(), rating.getEmployee().getId());
        Rating savedRating = ratingRepository.save(rating);
        logger.debug("Rating saved with ID: {}", savedRating.getRatingId());
        return savedRating;
    }

    @Override
    public List<RatingDTO> getRatingsForVendor(Long vendorId) {
        logger.info("Fetching ratings for vendor ID: {}", vendorId);
        List<Rating> ratings = ratingRepository.findByVendorId(vendorId);
        logger.debug("Found {} ratings for vendor ID: {}", ratings.size(), vendorId);
        return ratings.stream()
                .map(RatingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rating> getRatingsByEmployee(Long employeeId) {
        logger.info("Fetching ratings by employee ID: {}", employeeId);
        return ratingRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Rating updateRating(Long ratingId, Rating updatedRating) {
        logger.info("Updating rating with ID: {}", ratingId);
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> {
                    logger.error("Rating not found with ID: {}", ratingId);
                    return new RuntimeException("Rating not found with ID: " + ratingId);
                });

        existingRating.setRatingValue(updatedRating.getRatingValue());
        existingRating.setFeedback(updatedRating.getFeedback());
        existingRating.setRatingDate(updatedRating.getRatingDate());
        existingRating.setEmployee(updatedRating.getEmployee());
        existingRating.setVendor(updatedRating.getVendor());

        Rating savedRating = ratingRepository.save(existingRating);
        logger.info("Rating updated with ID: {}", savedRating.getRatingId());
        return savedRating;
    }

    public Double getAverageRatingVendor(Long vendorId) {
        logger.info("Calculating average rating for vendor ID: {}", vendorId);
        Double average = ratingRepository.findAverageRatingByVendorId(vendorId);
        logger.debug("Average rating for vendor ID {}: {}", vendorId, average);
        return average;
    }
}
