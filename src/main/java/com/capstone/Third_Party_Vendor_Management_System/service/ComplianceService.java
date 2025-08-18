package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;

import java.util.Optional;

public interface ComplianceService {

    // Get document by ID
    Optional<Compliance> getDocumentById(Long id);

    // Create a new document
    Compliance createDocument(Compliance doc);

    // Update an existing document
    Optional<Compliance> updateDocument(Long id, Compliance updatedDoc);

    // Delete a document
    boolean deleteDocument(Long id);
}
