package com.capstone.Third_Party_Vendor_Management_System.service;
import com.capstone.Third_Party_Vendor_Management_System.repository.AdminRepository;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin saveAdmin(Admin admin);
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(Long id);
    Admin updateAdmin(Long id, Admin updatedAdmin);
    void deleteAdmin(Long id);
}
