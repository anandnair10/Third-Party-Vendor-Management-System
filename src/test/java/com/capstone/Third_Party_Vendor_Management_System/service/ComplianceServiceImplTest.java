package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.Util.FileStorageUtil;
import com.capstone.Third_Party_Vendor_Management_System.Validator.ComplianceValidator;
import com.capstone.Third_Party_Vendor_Management_System.config.ComplianceDocumentsConfig;
import com.capstone.Third_Party_Vendor_Management_System.entities.Compliance;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.ComplianceRespository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.ComplianceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplianceServiceImplTest {

    @Mock
    private FileStorageUtil fileStorageUtil;

    @Mock
    private ComplianceValidator complianceValidator;

    @Mock
    private ComplianceDocumentsConfig complianceDocumentsConfig;

    @Mock
    private ComplianceRespository complianceRespository;

    @InjectMocks
    private ComplianceServiceImpl complianceService;

    @Test
    void testUploadComplianceDocuments_forHardwareVendor_success() throws IOException {
        Long vendorId = 101L;
        VendorType vendorType = VendorType.HARDWARE;

        MultipartFile gstFile = mock(MultipartFile.class);
        MultipartFile panFile = mock(MultipartFile.class);
        MultipartFile isoFile = mock(MultipartFile.class);
        MultipartFile shopLicenseFile = mock(MultipartFile.class);

        Map<String, MultipartFile> files = new HashMap<>();
        files.put("GST Certificate", gstFile);
        files.put("PAN card", panFile);
        files.put("ISO certification", isoFile);
        files.put("Shop License", shopLicenseFile);

        List<String> requiredDocs = Arrays.asList("GST Certificate", "PAN card", "ISO certification", "Shop License");

        when(complianceDocumentsConfig.getRequiredDocs(vendorType)).thenReturn(requiredDocs);
        when(fileStorageUtil.saveFile(any(MultipartFile.class), anyString(), anyString()))
                .thenAnswer(invocation -> "path/to/" + invocation.getArgument(2) + ".pdf");

        Map<String, String> result = (Map<String, String>) complianceService.uploadComplianceDocuments(vendorId, vendorType, files);

        assertEquals(4, result.size());
        verify(complianceValidator, times(4)).validateFile(any(), anyString());
        verify(complianceRespository, times(4)).save(any(Compliance.class));
    }


    @Test
    void testUploadComplianceDocuments_missingRequiredDoc_throwsException() {
        Long vendorId = 1L;
        VendorType vendorType = VendorType.HARDWARE;

        Map<String, MultipartFile> files = new HashMap<>();
        List<String> requiredDocs = List.of("GST", "PAN");

        when(complianceDocumentsConfig.getRequiredDocs(vendorType)).thenReturn(requiredDocs);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                complianceService.uploadComplianceDocuments(vendorId, vendorType, files));

        assertTrue(exception.getMessage().contains("Missing mandatory documents"));
    }
}
