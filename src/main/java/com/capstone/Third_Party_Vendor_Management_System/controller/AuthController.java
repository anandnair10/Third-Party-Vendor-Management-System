package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.LoginRequestDTO;
import com.capstone.Third_Party_Vendor_Management_System.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    public UserRepository userRepository;
    public AuthController(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request){
        String email = request.getEmail();
        String password = request.getPassword();
        return ResponseEntity.ok("login successfull");
    }
}
