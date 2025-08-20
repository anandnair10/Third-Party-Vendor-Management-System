package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;

import java.util.List;

public interface RatingService {

    /**
     * Adds a new rating to the system.
     * @param rating the Rating entity to be saved
     * @return the saved Rating
     */
    Rating addRating(Rating rating);

    /**
     * Retrieves all ratings associated with a specific vendor.
     * @param vendorId the ID of the vendor
     * @return list of RatingDTOs for the vendor
     */
    List<RatingDTO> getRatingsForVendor(Long vendorId);

    /**
     * Retrieves all ratings submitted by a specific employee.
     * @param employeeId the ID of the employee
     * @return list of ratings by the employee
     */
    List<Rating> getRatingsByEmployee(Long employeeId);

    /**
     * Updates an existing rating.
     * @param ratingId the ID of the rating to update
     * @param updatedRating the updated Rating entity
     * @return the updated Rating
     */
    Rating updateRating(Long ratingId, Rating updatedRating);

    public Double getAverageRatingVendor(Long vendorId);

    }
