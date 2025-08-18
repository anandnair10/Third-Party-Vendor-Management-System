package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.ComplianceDoc;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceDocRespository;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplianceDocServiceImpl implements ComplianceDocService {

    @Autowired
    private ComplianceDocRespository complianceDocRepository;

    @Override
    public Optional<ComplianceDoc> getDocumentById(Long id) {
        return complianceDocRepository.findById(id);
    }

    @Override
    public ComplianceDoc createDocument(ComplianceDoc doc) {
        return complianceDocRepository.save(doc);
    }

    @Override
    public Optional<ComplianceDoc> updateDocument(Long id, ComplianceDoc updatedDoc) {
        return complianceDocRepository.findById(id).map(doc -> {
            doc.setVendorId(updatedDoc.getVendorId());
            doc.setUploadDate(updatedDoc.getUploadDate());
            doc.setExpiryDate(updatedDoc.getExpiryDate());
            doc.setVerificationStatus(updatedDoc.getVerificationStatus());
            doc.setFilePath(updatedDoc.getFilePath());
            return complianceDocRepository.save(doc);
        });
    }

    @Override
    public boolean deleteDocument(Long id) {
        if (complianceDocRepository.existsById(id)) {
            complianceDocRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
