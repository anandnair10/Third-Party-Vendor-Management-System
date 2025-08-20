package com.capstone.Third_Party_Vendor_Management_System.service.Impl;


import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractExpiryReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractReminderSchedulerImpl {

    private final ContractExpiryReminderRepository reminderRepository;

    // Make this public so it can be called manually
    public void sendReminders() {
        List<ContractExpiryReminder> reminders = reminderRepository.findByReminderDateAndReminderSent(LocalDate.now(), false);

        for (ContractExpiryReminder reminder : reminders) {
            Long vendorId = reminder.getContract().getVendorId();
            LocalDate endDate = reminder.getContract().getEndDate();

            System.out.println("Reminder: Contract for vendor " + vendorId + " is due on " + endDate);

            reminder.setReminderSent(true);
            reminderRepository.save(reminder);
        }
    }
}
