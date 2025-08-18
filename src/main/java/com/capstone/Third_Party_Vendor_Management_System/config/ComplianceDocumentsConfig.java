package com.capstone.Third_Party_Vendor_Management_System.config;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;

import java.util.*;

public class ComplianceDocumentsConfig {
    private static final Map<VendorType, List<String>> requiredDocs =new HashMap<>();
    static{
        requiredDocs.put(VendorType.HARDWARE,
                Arrays.asList("GST Certificate","PAN card","ISO certification","Shop License"));
        requiredDocs.put(VendorType.CATERING,
                Arrays.asList("FSSAI License","GST certificate","PAN card","Fire safety certificate"));
        requiredDocs.put(VendorType.SUPPORT,
                Arrays.asList("Labour License","GST Certificate","PAN card","Service Agreement"));
    }
    public static List<String> getRequiredDocs(VendorType type){
        return requiredDocs.getOrDefault(type, Collections.emptyList());
    }
}
