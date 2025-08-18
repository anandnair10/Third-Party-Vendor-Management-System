package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor){
        Vendor savedVendor =vendorService.registerVendor(vendor);
        return ResponseEntity.ok(savedVendor);
    }
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors(){
        return ResponseEntity.ok(vendorService.getAllVendors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor){
        return ResponseEntity.ok(vendorService.updateVendor(id, vendor));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Vendor> deleteVendor(@PathVariable Long id){
        return ResponseEntity.ok(vendorService.deleteVendor(id));
    }
}
