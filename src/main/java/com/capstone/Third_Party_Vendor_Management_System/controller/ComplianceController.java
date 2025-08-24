package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VerificationStatus;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance-docs")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceDocService;

    public ComplianceController(ComplianceService complianceDocService) {
        this.complianceDocService = complianceDocService;
    }

    // Create a new document
    @PostMapping("/upload/{vendorId}")
    public ResponseEntity<List<String>> uploadComplianceDocs(
            @PathVariable Long vendorId,
            @RequestParam VendorType vendorType,
            @RequestParam Map<String, MultipartFile> files) throws IOException {
        List<String> storedDocs =  complianceDocService.uploadComplianceDocuments(vendorId, vendorType, files);
        return ResponseEntity.ok(storedDocs);
    }

    //get documents by ID
    @GetMapping("/getDocs/{vendorId}")
    public ResponseEntity<List<Compliance>> getVendorDocs(@PathVariable Long vendorId){
        return ResponseEntity.ok(complianceDocService.getDocumentByVendorId(vendorId));
    }

    // Delete a document
    @DeleteMapping("/deleteDoc/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        boolean deleted = complianceDocService.deleteDocument(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    //updating verification status after manual verification by admins
    //http://localhost:8080/api/compliance-docs/update-status/vendor/8?status=APPROVED
    @PatchMapping("/update-status/vendor/{vendorId}")
    public ResponseEntity<String> updateDocumentsByVendor(
            @PathVariable Long vendorId,
            @RequestParam String status) {

        try {
            VerificationStatus verificationStatus = VerificationStatus.valueOf(status.toUpperCase());
            boolean updated = complianceDocService.updateDocumentsByVendorId(vendorId, verificationStatus);
            if (updated) {
                return ResponseEntity.ok("All documents for vendor " + vendorId + " updated to " + verificationStatus);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No documents found for vendor " + vendorId);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + status);
        }
    }

}

