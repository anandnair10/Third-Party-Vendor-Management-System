package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.VendorType;
import com.capstone.Third_Party_Vendor_Management_System.repository.VendorRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.VendorServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendorServiceImplTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorServiceImpl vendorService;
    private Vendor vendor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vendor = new Vendor();
        vendor.setEmailAddress("vendor@example.com");
        vendor.setWebsite("www.vendor.com");
        vendor.setCompanyAddress("123 Street, City");
        vendor.setVendorType(VendorType.CATERING);
        vendor.setDescription("Provides IT solutions");
        vendor.setPricing("Competitive");
    }

    @Test
    void testRegisterVendor() {
        when(vendorRepository.save(vendor)).thenReturn(vendor);
        Vendor savedVendor = vendorService.registerVendor(vendor);
        assertEquals(vendor, savedVendor);
    }

    @Test
    void testGetVendorById_Success() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        Vendor result = vendorService.getVendorById(1L);
        assertEquals(vendor, result);
    }

    @Test
    void testGetVendorById_NotFound() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> vendorService.getVendorById(1L));
        assertEquals("Vendor not found with id 1", exception.getMessage());
    }

    @Test
    void testGetAllVendors() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Vendor> vendorsList = List.of(vendor);
        Page<Vendor> page = new PageImpl<>(vendorsList, pageable, vendorsList.size());

        when(vendorRepository.findAll(pageable)).thenReturn(page);

        Page<Vendor> result = vendorService.getAllVendors(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(vendor, result.getContent().get(0));
    }


    @Test
    void testUpdateVendor_Success() {
        Vendor updatedVendor = new Vendor();
        updatedVendor.setFullName("Updated Name");
        updatedVendor.setBusinessName("Updated Business");
        updatedVendor.setBusinessStructure("LLP");
        updatedVendor.setEstablishedYear(2015);
        updatedVendor.setPrimaryContactDesignation("Director");
        updatedVendor.setPrimaryContactName("Jane Doe");
        updatedVendor.setPhoneNumber("1234567890");
        updatedVendor.setEmailAddress("updated@example.com");
        updatedVendor.setWebsite("www.updated.com");
        updatedVendor.setCompanyAddress("456 Avenue, City");
        updatedVendor.setVendorType(VendorType.CATERING);
        updatedVendor.setDescription("Updated description");
        updatedVendor.setPricing("Premium");

        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(updatedVendor);

        Vendor result = vendorService.updateVendor(1L, updatedVendor);
        assertEquals("Updated Name", result.getFullName());
        assertEquals("Updated Business", result.getBusinessName());
    }

    @Test
    void testUpdateVendor_NotFound() {
        Vendor updatedVendor = new Vendor();
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> vendorService.updateVendor(1L, updatedVendor));
        assertEquals("Vendor not found with id 1", exception.getMessage());
    }

    @Test
    void testDeleteVendor_Success() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        doNothing().when(vendorRepository).delete(vendor);

        Vendor deletedVendor = vendorService.deleteVendor(1L);
        assertEquals(vendor, deletedVendor);
        verify(vendorRepository, times(1)).delete(vendor);
    }

    @Test
    void testDeleteVendor_NotFound() {
        when(vendorRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> vendorService.deleteVendor(1L));
        assertEquals("Vendor not found with ID: 1", exception.getMessage());
    }
}

