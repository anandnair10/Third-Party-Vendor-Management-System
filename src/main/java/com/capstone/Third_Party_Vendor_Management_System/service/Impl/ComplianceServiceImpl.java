package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComplianceServiceImpl {

    private final FileStorageUtil fileStorageUtil;
    private final ComplianceValidator complianceValidator;
    private final ComplianceDocumentsConfig complianceDocumentsConfig;
    private final ComplianceRespository complianceRespository;
    private final VendorRepository vendorRepository;

    public ComplianceServiceImpl(FileStorageUtil fileStorageUtil,
                                 ComplianceValidator complianceValidator,
                                 ComplianceDocumentsConfig complianceDocumentsConfig,
                                 ComplianceRespository complianceRespository,
                                 VendorRepository vendorRepository) {
        this.fileStorageUtil = fileStorageUtil;
        this.complianceValidator = complianceValidator;
        this.complianceDocumentsConfig = complianceDocumentsConfig;
        this.complianceRespository = complianceRespository;
        this.vendorRepository = vendorRepository;
    }

    public Map<String, String> uploadComplianceDocuments(Long vendorId, VendorType vendorType,
                                                         Map<String, MultipartFile> files) throws IOException {

        // Fetch vendor entity to set relation in Compliance entity
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (vendorOpt.isEmpty()) {
            throw new RuntimeException("Vendor with ID " + vendorId + " not found");
        }
        Vendor vendor = vendorOpt.get();

        Map<String, String> storedPaths = new HashMap<>();
        List<String> requiredDocs = complianceDocumentsConfig.getRequiredDocs(vendorType);
        for (String doc : requiredDocs) {
            if (!files.containsKey(doc)) {
                throw new RuntimeException("Missing mandatory document: " + doc);
            }
        }

        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String docName = entry.getKey();
            MultipartFile file = entry.getValue();

            complianceValidator.validateFile(file, docName);
            String path = fileStorageUtil.saveFile(file, vendorId.toString(), docName);
            storedPaths.put(docName, path);

            Compliance complianceDoc = new Compliance();
            complianceDoc.setDocumentName(docName);  // Corrected method name casing
            complianceDoc.setFilePath(path);
            complianceDoc.setVendor(vendor); // Set vendor entity, not vendorId

            complianceRespository.save(complianceDoc);
        }

        return storedPaths;
    }
}
