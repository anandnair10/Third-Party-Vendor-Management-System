package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.ComplianceDoc;

import java.util.List;
import java.util.Optional;

public interface ComplianceDocService {

    // Get document by ID
    Optional<ComplianceDoc> getDocumentById(Long id);

    // Create a new document
    ComplianceDoc createDocument(ComplianceDoc doc);

    // Update an existing document
    Optional<ComplianceDoc> updateDocument(Long id, ComplianceDoc updatedDoc);

    // Delete a document
    boolean deleteDocument(Long id);
}
