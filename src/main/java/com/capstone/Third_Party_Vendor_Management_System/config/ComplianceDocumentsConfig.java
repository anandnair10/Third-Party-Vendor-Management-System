package com.capstone.Third_Party_Vendor_Management_System.config;

import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import org.springframework.context.annotation.Configuration;

import java.util.*;
@Configuration
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

    // just making some comments to check the git status
    // delete it once the complete push is done
    // git status says branch uptodate
    // trying git push
}
