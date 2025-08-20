package com.capstone.Third_Party_Vendor_Management_System.controller;
import com.capstone.Third_Party_Vendor_Management_System.dto.AdminDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import com.capstone.Third_Party_Vendor_Management_System.mapper.AdminMapper;
import com.capstone.Third_Party_Vendor_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = AdminMapper.toEntity(adminDTO);
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(AdminMapper.toDTO(savedAdmin));
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins()
                .stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        Admin updatedAdmin = adminService.updateAdmin(id, AdminMapper.toEntity(adminDTO));
        return ResponseEntity.ok(AdminMapper.toDTO(updatedAdmin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        Optional<Admin> adminOptional = adminService.getAdminById(id);
        return adminOptional
                .map(admin -> ResponseEntity.ok(AdminMapper.toDTO(admin)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
