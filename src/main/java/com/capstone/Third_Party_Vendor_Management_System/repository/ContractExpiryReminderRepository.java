package com.capstone.Third_Party_Vendor_Management_System.repository;

import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContractExpiryReminderRepository extends JpaRepository<ContractExpiryReminder, Long> {
    List<ContractExpiryReminder> findByReminderDateAndReminderSent(LocalDate reminderDate, boolean reminderSent);
}

