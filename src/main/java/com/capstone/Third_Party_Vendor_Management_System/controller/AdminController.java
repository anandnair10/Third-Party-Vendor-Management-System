package com.capstone.Third_Party_Vendor_Management_System.controller;
import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import com.capstone.Third_Party_Vendor_Management_System.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        return ResponseEntity.ok(adminService.saveAdmin(admin));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.getAdminById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id){
        return ResponseEntity.ok(adminService.updateAdmin(id,updatedAdmin));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
