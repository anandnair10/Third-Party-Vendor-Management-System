package com.capstone.Third_Party_Vendor_Management_System.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contract_expiry_reminders")
public class ContractExpiryReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reminderDate;

    private boolean reminderSent;

    @OneToOne
    @JoinColumn(name = "contract_id")
    @JsonBackReference
    private Contract contract;
}
