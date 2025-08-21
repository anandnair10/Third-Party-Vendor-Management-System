package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VerificationStatus;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    private static final Logger logger = LoggerFactory.getLogger(ComplianceServiceImpl.class);

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

    public List<String> uploadComplianceDocuments(Long vendorId, VendorType vendorType,
                                                  Map<String, MultipartFile> files) throws IOException {

        logger.info("Uploading compliance documents for vendor ID: {}", vendorId);

        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (vendorOpt.isEmpty()) {
            logger.error("Vendor with ID {} not found", vendorId);
            throw new RuntimeException("Vendor with ID " + vendorId + " not found");
        }
        Vendor vendor = vendorOpt.get();

        List<String> storedPaths = new ArrayList<>();
        List<String> requiredDocs = complianceDocumentsConfig.getRequiredDocs(vendorType);
        for (String doc : requiredDocs) {
            if (!files.containsKey(doc)) {
                logger.error("Missing mandatory document: {}", doc);
                throw new RuntimeException("Missing mandatory document: " + doc);
            }
        }

        for (Map.Entry<String, MultipartFile> entry : files.entrySet()) {
            String docName = entry.getKey();
            MultipartFile file = entry.getValue();

            logger.debug("Validating and saving document: {}", docName);
            complianceValidator.validateFile(file, docName);
            String path = fileStorageUtil.saveFile(file, vendorId.toString(), docName);
            storedPaths.add(file.getOriginalFilename());

            Compliance complianceDoc = new Compliance();
            complianceDoc.setVendorId(vendorId);
            complianceDoc.setDocumentName(docName);
            complianceDoc.setFilePath(path);
            complianceDoc.setUploadDate(LocalDate.now());
            complianceDoc.setUploadedAt(LocalDateTime.now());
            complianceDoc.setVerificationStatus(VerificationStatus.PENDING);

            complianceRespository.save(complianceDoc);
            logger.info("Saved compliance document: {} for vendor ID: {}", docName, vendorId);
        }

        return storedPaths;
    }

    @Override
    public List<Compliance> getDocumentByVendorId(Long vendorId) {
        logger.info("Fetching documents for vendor ID: {}", vendorId);
        return complianceRespository.findByVendorId(vendorId);
    }

    @Override
    public boolean deleteDocument(Long id) {
        logger.info("Attempting to delete document with ID: {}", id);
        if (complianceRespository.existsById(id)) {
            complianceRespository.deleteById(id);
            logger.info("Deleted document with ID: {}", id);
            return true;
        }
        logger.warn("Document with ID {} not found", id);
        return false;
    }
}
