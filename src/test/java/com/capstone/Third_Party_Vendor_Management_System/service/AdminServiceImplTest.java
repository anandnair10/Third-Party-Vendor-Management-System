package com.capstone.Third_Party_Vendor_Management_System.service;


import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import com.capstone.Third_Party_Vendor_Management_System.entities.enums.Role;
import com.capstone.Third_Party_Vendor_Management_System.repository.AdminRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Admin();
        admin.setId(1L);
        admin.setFullName("Admin One");
        admin.setEmail("admin@example.com");
        admin.setPhoneNumber("9876543210");
        admin.setRole(Role.ADMIN);
        admin.setPasswordHash("secureHash");
    }

    @Test
    void testSaveAdmin() {
        when(adminRepository.save(admin)).thenReturn(admin);
        Admin savedAdmin = adminService.saveAdmin(admin);
        assertEquals(admin, savedAdmin);
    }

    @Test
    void testGetAllAdmins() {
        List<Admin> admins = List.of(admin);
        when(adminRepository.findAll()).thenReturn(admins);
        List<Admin> result = adminService.getAllAdmins();
        assertEquals(1, result.size());
        assertEquals(admin, result.get(0));
    }

    @Test
    void testGetAdminById() {
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        Optional<Admin> result = adminService.getAdminById(1L);
        assertTrue(result.isPresent());
        assertEquals(admin, result.get());
    }

    @Test
    void testUpdateAdmin() {
        Admin updatedAdmin = new Admin();
        updatedAdmin.setFullName("Updated Admin");
        updatedAdmin.setEmail("updated@example.com");
        updatedAdmin.setPhoneNumber("1234567890");
        updatedAdmin.setRole(Role.ADMIN);
        updatedAdmin.setPasswordHash("newHash");

        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        Admin result = adminService.updateAdmin(1L, updatedAdmin);
        assertEquals("Updated Admin", result.getFullName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminRepository).deleteById(1L);
        adminService.deleteAdmin(1L);
        verify(adminRepository, times(1)).deleteById(1L);
    }
}

