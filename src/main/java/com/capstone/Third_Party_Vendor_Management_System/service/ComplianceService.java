package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VerificationStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ComplianceService {

    List<Compliance> getDocumentByVendorId(Long VendorId);

    // Delete a document
    boolean deleteDocument(Long id);

    List<String> uploadComplianceDocuments(Long vendorId, VendorType vendorType, Map<String, MultipartFile> files) throws IOException;

    boolean updateDocumentsByVendorId(Long vendorId, VerificationStatus status);

    //boolean updateDocumentsByVendorId(Long vendorId, String status);
}
