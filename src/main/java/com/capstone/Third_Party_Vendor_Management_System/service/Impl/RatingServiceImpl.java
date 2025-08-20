package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.mapper.RatingMapper;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

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
        return ratingRepository.save(rating);
    }

    @Override
    public List<RatingDTO> getRatingsForVendor(Long vendorId) {
        List<Rating> ratings = ratingRepository.findByVendorId(vendorId);

        return ratings.stream()
                .map(RatingMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<Rating> getRatingsByEmployee(Long employeeId) {
        return ratingRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Rating updateRating(Long ratingId, Rating updatedRating) {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with ID: " + ratingId));

        existingRating.setRatingValue(updatedRating.getRatingValue());
        existingRating.setFeedback(updatedRating.getFeedback());
        existingRating.setRatingDate(updatedRating.getRatingDate());
        existingRating.setEmployee(updatedRating.getEmployee());
        existingRating.setVendor(updatedRating.getVendor());

        return ratingRepository.save(existingRating);
    }
//  http://localhost:8080/api/rating/vendor/8/average
    public Double getAverageRatingVendor(Long vendorId){
        return ratingRepository.findAverageRatingByVendorId(vendorId);
    }
}
