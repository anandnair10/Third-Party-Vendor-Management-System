package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import com.capstone.Third_Party_Vendor_Management_System.repository.AdminRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin saveAdmin(Admin admin){
        logger.info("Saving admin: {}", admin);
        String hashedPassword = passwordEncoder.encode(admin.getPasswordHash());
        admin.setPasswordHash(hashedPassword);
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins(){
        logger.info("Fetching all admins");
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id){
        logger.info("Fetching admin with ID: {}", id);
        return adminRepository.findById(id);
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin){
        logger.info("Updating admin with ID: {}", id);
        return adminRepository.findById(id).map(admin -> {
            admin.setFullName(updatedAdmin.getFullName());
            admin.setEmail(updatedAdmin.getEmail());
            admin.setPhoneNumber(updatedAdmin.getPhoneNumber());
            admin.setRole(updatedAdmin.getRole());
            String hashedPassword = passwordEncoder.encode(updatedAdmin.getPasswordHash());
            admin.setPasswordHash(hashedPassword);
            logger.info("Updated admin: {}", admin);
            return adminRepository.save(admin);
        }).orElseThrow(() -> {
            logger.error("Admin not found with ID: {}", id);
            return new RuntimeException("Admin not found with id " + id);
        });
    }

    public void deleteAdmin(Long id){
        logger.info("Deleting admin with ID: {}", id);
        adminRepository.deleteById(id);
    }
}
