package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplianceServiceImpl implements ComplianceService {

    @Autowired
    private ComplianceRespository complianceDocRepository;

    @Override
    public Optional<Compliance> getDocumentById(Long id) {
        return complianceDocRepository.findById(id);
    }

    @Override
    public Compliance createDocument(Compliance doc) {
        return complianceDocRepository.save(doc);
    }

    @Override
    public Optional<Compliance> updateDocument(Long id, Compliance updatedDoc) {
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
