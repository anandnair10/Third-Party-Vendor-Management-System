package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
