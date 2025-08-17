package com.capstone.Third_Party_Vendor_Management_System.service;
import com.capstone.Third_Party_Vendor_Management_System.repository.AdminRepository;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    AdminRepository adminRepository;
    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository=adminRepository;
    }

    public Admin saveAdmin(Admin admin){
        return adminRepository.save(admin);
    }
    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }
    public Admin getAdminById(Long id){
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
    }
    public Admin updateAdmin(Long id, Admin updatedAdmin){
        return adminRepository.findById(id).map(admin -> {
            admin.setFullName(updatedAdmin.getFullName());
            admin.setEmail(updatedAdmin.getEmail());
            admin.setPhoneNumber(updatedAdmin.getPhoneNumber());
            admin.setRole(updatedAdmin.getRole());
            admin.setPasswordHash(updatedAdmin.getPasswordHash());
            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found with id "+ id));
    }
    public void deleteAdmin(Long id){
        adminRepository.deleteById(id);
    }
}
