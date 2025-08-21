package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.TopRatedVendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.dto.VendorDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.mapper.VendorMapper;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/createVendor")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor savedVendor =vendorService.registerVendor(vendor);
        return ResponseEntity.ok(savedVendor);
    }

    @GetMapping("/getAllVendor")
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorService.getAllVendors()
                .stream()
                .map(VendorMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/getVendor/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    @PutMapping("/updateVendor/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor){
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }
    @DeleteMapping("/deleteVendor/{id}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.deleteVendor(id));
    }

    @GetMapping("/sorted-by-rating")
    public ResponseEntity<List<TopRatedVendorDTO>> getVendorsSortedByRatingDesc() {
        List<TopRatedVendorDTO> sortedVendors = vendorService.getVendorsSortedByRatingDesc();
        return ResponseEntity.ok(sortedVendors);
    }
}
