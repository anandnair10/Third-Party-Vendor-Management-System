package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
