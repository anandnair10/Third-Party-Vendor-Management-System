package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.ComplianceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComplianceServiceImplTest {

    @Mock
    private FileStorageUtil fileStorageUtil;

    @Mock
    private ComplianceValidator complianceValidator;

    @Mock
    private ComplianceDocumentsConfig complianceDocumentsConfig;

    @Mock
    private ComplianceRespository complianceRespository;

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private ComplianceServiceImpl complianceService;

    private Vendor vendor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendor = new Vendor();
        vendor.setId(1L);
        vendor.setVendorType(VendorType.CATERING);

    }

    @Test
    void testUploadComplianceDocuments_Success() throws IOException {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class); // ✅ Proper mock
        when(mockFile.getOriginalFilename()).thenReturn("document.pdf");

        Map<String, MultipartFile> files = new HashMap<>();
        files.put("PAN_CARD", mockFile);

        List<String> requiredDocs = List.of("PAN_CARD");

        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(complianceDocumentsConfig.getRequiredDocs(VendorType.CATERING)).thenReturn(requiredDocs);
        when(fileStorageUtil.saveFile(mockFile, "1", "PAN_CARD")).thenReturn("/path/to/document.pdf");

        List<String> result = complianceService.uploadComplianceDocuments(1L, VendorType.CATERING, files);

        assertEquals(1, result.size());
        assertEquals("document.pdf", result.get(0));
        verify(complianceRespository, times(1)).save(any(Compliance.class));
    }


    @Test
    void testUploadComplianceDocuments_MissingVendor() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                complianceService.uploadComplianceDocuments(1L, VendorType.CATERING, new HashMap<>()));

        assertEquals("Vendor with ID 1 not found", exception.getMessage());
    }

    @Test
    void testUploadComplianceDocuments_MissingDocument() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(complianceDocumentsConfig.getRequiredDocs(VendorType.CATERING)).thenReturn(List.of("GST_CERT"));

        Map<String, MultipartFile> files = new HashMap<>();
        files.put("PAN_CARD", mock(MultipartFile.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                complianceService.uploadComplianceDocuments(1L, VendorType.CATERING, files));

        assertEquals("Missing mandatory document: GST_CERT", exception.getMessage());
    }

    @Test
    void testGetDocumentByVendorId() {
        List<Compliance> compliances = List.of(new Compliance());
        when(complianceRespository.findByVendorId(1L)).thenReturn(compliances);

        List<Compliance> result = complianceService.getDocumentByVendorId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testDeleteDocument_Success() {
        when(complianceRespository.existsById(1L)).thenReturn(true);
        doNothing().when(complianceRespository).deleteById(1L);

        boolean result = complianceService.deleteDocument(1L);

        assertTrue(result);
        verify(complianceRespository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDocument_Failure() {
        when(complianceRespository.existsById(1L)).thenReturn(false);

        boolean result = complianceService.deleteDocument(1L);

        assertFalse(result);
        verify(complianceRespository, never()).deleteById(anyLong());
    }
}
