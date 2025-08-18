package com.capstone.Third_Party_Vendor_Management_System.Validator;

import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;

import java.util.List;

public class ComplianceValidator {
    public static boolean validate(VendorType vendorType, List<String> uploadedDocs){
        List<String> requiredDocs = ComplianceDocumentsConfig.getRequiredDocs(vendorType);
        return uploadedDocs.containsAll(requiredDocs);
    }
    public static List<String> getMissingDocs(VendorType vendorType, List<String> uploadedDocs){
        List<String> requiredDocs =ComplianceDocumentsConfig.getRequiredDocs(vendorType);
        return requiredDocs.stream()
                .filter(doc -> !uploadedDocs.contains(doc))
                .toList();
    }
}
