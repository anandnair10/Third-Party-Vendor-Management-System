package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.RatingDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Rating;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.mapper.RatingMapper;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.VendorServiceImpl;
import com.capstone.Third_Party_Vendor_Management_System.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Vendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Rating rating = RatingMapper.toEntity(dto, employee, vendor);
        Rating savedRating = ratingService.addRating(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @PutMapping("/{ratingId}")
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

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<RatingDTO>> getRatingsByVendor(@PathVariable Long vendorId) {
        List<RatingDTO> response = ratingService.getRatingsForVendor(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}/average")
    public ResponseEntity<Double> getAverageRatingVendor(@PathVariable Long vendorId){
        Double avgRating = ratingService.getAverageRatingVendor(vendorId);
        if(avgRating == null){
            return ResponseEntity.ok(0.0);
        }
        return ResponseEntity.ok(avgRating);
    }


}
