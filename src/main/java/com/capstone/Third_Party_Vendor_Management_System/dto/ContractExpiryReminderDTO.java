package com.capstone.Third_Party_Vendor_Management_System.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractExpiryReminderDTO {
    private Long id;
    private LocalDate reminderDate;
    private Long contractId;
}
