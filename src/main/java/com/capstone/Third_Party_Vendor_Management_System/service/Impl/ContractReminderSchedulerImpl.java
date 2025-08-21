package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractExpiryReminderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractReminderSchedulerImpl {

    private static final Logger logger = LoggerFactory.getLogger(ContractReminderSchedulerImpl.class);

    private final ContractExpiryReminderRepository reminderRepository;

    // Make this public so it can be called manually
    public void sendReminders() {
        logger.info("Starting contract expiry reminder check for date: {}", LocalDate.now());

        List<ContractExpiryReminder> reminders = reminderRepository.findByReminderDateAndReminderSent(LocalDate.now(), false);

        if (reminders.isEmpty()) {
            logger.info("No contract expiry reminders to send today.");
        }

        for (ContractExpiryReminder reminder : reminders) {
            Long vendorId = reminder.getContract().getVendorId();
            LocalDate endDate = reminder.getContract().getEndDate();

            logger.info("Sending reminder: Contract for vendor {} is due on {}", vendorId, endDate);

            reminder.setReminderSent(true);
            reminderRepository.save(reminder);

            logger.debug("Marked reminder as sent for vendor ID: {}", vendorId);
        }

        logger.info("Completed sending contract expiry reminders.");
    }
}
