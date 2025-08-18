package com.capstone.Third_Party_Vendor_Management_System.Validator;

import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class ComplianceValidator {
    private static final List<String> ALLOWED_FORMATS = List.of("application/pdf","image/png","image/jpeg");
    private static final long MAX_FILE_SIZE = 5*1024*1024;
    public static boolean validate(VendorType vendorType, List<String> uploadedDocs){
        List<String> requiredDocs = ComplianceDocumentsConfig.getRequiredDocs(vendorType);
        return uploadedDocs.containsAll(requiredDocs);
    }
    public static void validateFile(MultipartFile file, String docName){
        if(file.isEmpty()){
            throw new RuntimeException("File "+docName+ " is empty");
        }
        if(!ALLOWED_FORMATS.contains(file.getContentType())){
            throw new RuntimeException("Invalid file format");
        }
        if(file.getSize()>MAX_FILE_SIZE){
            throw new RuntimeException("file exceeds max size");
        }
    }
    public static List<String> getMissingDocs(VendorType vendorType, List<String> uploadedDocs){
        List<String> requiredDocs =ComplianceDocumentsConfig.getRequiredDocs(vendorType);
        return requiredDocs.stream()
                .filter(doc -> !uploadedDocs.contains(doc))
                .toList();
    }
    public static Map<String, String> validateFileProperties(Map<String, MultipartFile> uploadedFiles){
        Map<String, String> errors= new HashMap<>();
        for(Map.Entry<String, MultipartFile> entry : uploadedFiles.entrySet()){
            String docName= entry.getKey();
            MultipartFile file = entry.getValue();
            if(file.getSize() > MAX_FILE_SIZE){
                errors.put(docName, "File too large, Max size is 5MB");
            }
        }
        return errors;
    }
}
