package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
