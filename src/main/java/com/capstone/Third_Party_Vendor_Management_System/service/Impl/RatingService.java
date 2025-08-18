package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    //Save Rating
    public Rating saveRating(Rating rating){
        return  ratingRepository.save(rating);
    }
    // get rating by vendor id( to do)

    // sorting by rating

}
