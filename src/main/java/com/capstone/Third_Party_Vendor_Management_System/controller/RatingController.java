package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.mapper.RatingMapper;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VendorRepository vendorRepository;

    //giving the rating to the vendors

    @PostMapping("/Rating")
    public ResponseEntity<Rating> createRating(@RequestBody RatingDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Rating rating = RatingMapper.toEntity(dto, employee, vendor);
        Rating savedRating = ratingService.addRating(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }


    //updating their rating

    @PutMapping("/updateRating/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId,
                                               @RequestBody RatingDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Rating updatedRating = RatingMapper.toEntity(dto, employee, vendor);
        Rating savedRating = ratingService.updateRating(ratingId, updatedRating);

        return ResponseEntity.ok(savedRating);
    }


    //get ratings by their IDs
    @GetMapping("/getRatingByID/{vendorId}")

    public ResponseEntity<List<RatingDTO>> getRatingsByVendor(@PathVariable Long vendorId) {
        List<RatingDTO> response = ratingService.getRatingsForVendor(vendorId);
        return ResponseEntity.ok(response);
    }


    //get average rating of each vendor

    @GetMapping("/Average/{vendorId}")
    public ResponseEntity<Double> getAverageRatingVendor(@PathVariable Long vendorId){
        Double avgRating = ratingService.getAverageRatingVendor(vendorId);
        if(avgRating == null){
            return ResponseEntity.ok(0.0);
        }
        return ResponseEntity.ok(avgRating);
    }

}
