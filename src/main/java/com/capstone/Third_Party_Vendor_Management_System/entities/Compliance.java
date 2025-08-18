package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "compliance_docs")
public class Compliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name ="document_name", nullable = false)
    private String documentName;

    @Column(name = "file_path", columnDefinition = "TEXT")
    private String filePath;

    @Column(name =" uploaded_at", nullable = false)
    private LocalDateTime uploadedAt =LocalDateTime.now();
}
