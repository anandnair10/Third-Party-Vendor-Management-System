package com.capstone.Third_Party_Vendor_Management_System.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "rating_value", nullable = false)
    private Integer ratingValue;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    @Column(name = "rating_date")
    private LocalDate ratingDate;
}
