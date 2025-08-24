package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.mapper.VendorMapper;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    //create vendor
    @PostMapping("/createVendor")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor savedVendor =vendorService.registerVendor(vendor);
        return ResponseEntity.ok(savedVendor);
    }

    //get list of vendors
    @GetMapping("/VendorList")
    public ResponseEntity<Page<VendorDTO>> getAllVendors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VendorDTO> vendorPage = vendorService.getAllVendors(pageable)
                .map(VendorMapper::toDTO);
        return ResponseEntity.ok(vendorPage);
    }

    //get vendor by ID
    @GetMapping("/getVendor/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    //update vendor by ID
    @PutMapping("/updateVendor/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor){
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }

    //delete vendor by ID
    @DeleteMapping("/deleteVendor/{id}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.deleteVendor(id));
    }

    //sort vendors by rating
    @GetMapping("/sorted-by-rating")
    public ResponseEntity<List<TopRatedVendorDTO>> getVendorsSortedByRatingDesc() {
        List<TopRatedVendorDTO> sortedVendors = vendorService.getVendorsSortedByRatingDesc();
        return ResponseEntity.ok(sortedVendors);
    }
}
