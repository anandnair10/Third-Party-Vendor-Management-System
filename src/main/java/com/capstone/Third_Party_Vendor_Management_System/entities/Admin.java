package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin extends User {

    @Column(name = "admin_code", nullable = false, unique = true)
    private String adminCode;

    @Column(name = "department")
    private String department;
}
