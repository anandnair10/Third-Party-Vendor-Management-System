package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.entities.ComplianceDoc;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compliance-docs")
public class ComplianceDocController {

    @Autowired
    private ComplianceDocService complianceDocService;

    // Get document by ID
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceDoc> getDocumentById(@PathVariable Long id) {
        Optional<ComplianceDoc> doc = complianceDocService.getDocumentById(id);
        return doc.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new document
    @PostMapping
    public ResponseEntity<ComplianceDoc> createDocument(@RequestBody ComplianceDoc doc) {
        ComplianceDoc created = complianceDocService.createDocument(doc);
        return ResponseEntity.ok(created);
    }

    // Update an existing document
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceDoc> updateDocument(@PathVariable Long id, @RequestBody ComplianceDoc updatedDoc) {
        Optional<ComplianceDoc> updated = complianceDocService.updateDocument(id, updatedDoc);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        boolean deleted = complianceDocService.deleteDocument(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

