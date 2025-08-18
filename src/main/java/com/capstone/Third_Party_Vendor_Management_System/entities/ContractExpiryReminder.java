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
@Table(name = "contract_expiry_reminders")
public class ContractExpiryReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reminderDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", unique = true, nullable = false)
    private Contract contract;
}
