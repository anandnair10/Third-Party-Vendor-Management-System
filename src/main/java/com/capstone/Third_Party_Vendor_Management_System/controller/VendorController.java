package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorRiskDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.RiskLevel;
import com.capstone.Third_Party_Vendor_Management_System.mapper.VendorMapper;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR')")
    //Create Vendor
    @PostMapping("/createVendor")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor savedVendor =vendorService.registerVendor(vendor);
        return ResponseEntity.ok(savedVendor);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    //Implementing Paging for Vendor Listing
    @GetMapping("/VendorList")
    public ResponseEntity<Page<VendorDTO>> getAllVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VendorDTO> vendorPage = vendorService.getAllVendors(pageable)
                .map(VendorMapper::toDTO);
        return ResponseEntity.ok(vendorPage);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    //Get Vendor by Id
    @GetMapping("/getVendor/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }


    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    //Update Vendor by ID
    @PutMapping("/updateVendor/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor){
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    //Delete Vendor by ID
    @DeleteMapping("/deleteVendor/{id}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.deleteVendor(id));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    //Sort Vendor by Rating
    @GetMapping("/sorted-by-rating")
    public ResponseEntity<List<TopRatedVendorDTO>> getVendorsSortedByRatingDesc() {
        List<TopRatedVendorDTO> sortedVendors = vendorService.getVendorsSortedByRatingDesc();
        return ResponseEntity.ok(sortedVendors);
    }

    // Return vendors sorted by risk level
    @GetMapping("/sorted-by-risk")
    public ResponseEntity<List<VendorRiskDTO>> getSortedVendorsByRisk() {
        List<Vendor> vendors = vendorService.getAllVendor();

        List<VendorRiskDTO> dtos = vendors.stream()
                .map(v -> {
                    Double avgRating = vendorService.getAverageRatingVendor(v.getId());
                    RiskLevel risk = vendorService.calculateRiskLevel(avgRating);
                    return new VendorRiskDTO(v.getId(), v.getFullName(), avgRating, risk);
                })
                .sorted(Comparator.comparing(VendorRiskDTO::getRiskLevel)) // LOW → HIGH
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


}
