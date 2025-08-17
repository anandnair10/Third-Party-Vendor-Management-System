package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor

@Table(name = "admin")
public class Admin extends User{

    @Column(name = "admin_code", nullable = false, unique = true)
    private String adminCode;

    @Column(name = "department")
    private String department;
}
