package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceService;
import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ComplianceServiceImpl implements ComplianceService {

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

    @Override
    public Optional<Compliance> getDocumentById(Long id) {
        return complianceRespository.findById(id);
    }

    @Override
    public Compliance createDocument(Compliance doc) {
        return complianceRespository.save(doc);
    }

    @Override
    public Optional<Compliance> updateDocument(Long id, Compliance updatedDoc) {
        return complianceRespository.findById(id).map(existing -> {
            existing.setFilePath(updatedDoc.getFilePath());
            existing.setUploadDate(updatedDoc.getUploadDate());
            existing.setExpiryDate(updatedDoc.getExpiryDate());
            existing.setVerificationStatus(updatedDoc.getVerificationStatus());
            return complianceRespository.save(existing);
        });
    }

    @Override
    public boolean deleteDocument(Long id) {
        if (complianceRespository.existsById(id)) {
            complianceRespository.deleteById(id);
            return true;
        }
        return false;
    }

    public Map<String, String> uploadComplianceDocuments(Long vendorId, VendorType vendorType,
                                                         Map<String, MultipartFile> files) throws IOException {

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
            complianceDoc.setDocumentName(docName);
            complianceDoc.setFilePath(path);
            complianceDoc.setVendor(vendor);

            complianceRespository.save(complianceDoc);
        }

        return storedPaths;
    }
}
