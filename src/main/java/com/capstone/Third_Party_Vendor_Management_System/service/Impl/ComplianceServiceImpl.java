package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComplianceServiceImpl {
    private final FileStorageUtil fileStorageUtil;
    private final ComplianceValidator complianceValidator;
    private final ComplianceDocumentsConfig complianceDocumentsConfig;
    private final ComplianceRespository complianceRespository;

    public ComplianceServiceImpl(FileStorageUtil fileStorageUtil, ComplianceValidator complianceValidator, ComplianceDocumentsConfig complianceDocumentsConfig, ComplianceRespository complianceRespository) {
        this.fileStorageUtil = fileStorageUtil;
        this.complianceValidator = complianceValidator;
        this.complianceDocumentsConfig = complianceDocumentsConfig;
        this.complianceRespository = complianceRespository;
    }
    public Map<String, String> uploadComplianceDocuments(Long vendorId, VendorType vendorType,
                                                         Map<String, MultipartFile> files) throws IOException {
        Map<String, String> storedPaths =new HashMap<>();
        List<String> requiredDocs = complianceDocumentsConfig.getRequiredDocs(vendorType);
        for(String doc: requiredDocs){
            if(!files.containsKey(doc)){
                throw new RuntimeException("Missing mandatory documents"+ doc);
            }
        }
        for(Map.Entry<String, MultipartFile> entry: files.entrySet()){
            String docName = entry.getKey();
            MultipartFile file = entry.getValue();
            complianceValidator.validateFile(file,docName);
            String path= fileStorageUtil.saveFile(file,  vendorId.toString(),docName);
            storedPaths.put(docName, path);
            Compliance doc =new Compliance();
            doc.setVendorId(vendorId);
            doc.setDocumentName(docName);
            doc.setFilePath(path);
            complianceRespository.save(doc);
        }
        return storedPaths;
    }
}
