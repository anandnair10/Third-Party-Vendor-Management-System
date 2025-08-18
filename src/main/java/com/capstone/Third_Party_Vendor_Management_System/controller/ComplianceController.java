package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/compliance-docs")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceDocService;

    // Get document by ID
    @GetMapping("/{id}")
    public ResponseEntity<Compliance> getDocumentById(@PathVariable Long id) {
        Optional<Compliance> doc = complianceDocService.getDocumentById(id);
        return doc.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new document
    @PostMapping
    public ResponseEntity<Compliance> createDocument(@RequestBody Compliance doc) {
        Compliance created = complianceDocService.createDocument(doc);
        return ResponseEntity.ok(created);
    }

    // Update an existing document
    @PutMapping("/{id}")
    public ResponseEntity<Compliance> updateDocument(@PathVariable Long id, @RequestBody Compliance updatedDoc) {
        Optional<Compliance> updated = complianceDocService.updateDocument(id, updatedDoc);
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

