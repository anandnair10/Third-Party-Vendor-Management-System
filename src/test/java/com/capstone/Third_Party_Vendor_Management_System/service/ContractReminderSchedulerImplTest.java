package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;
import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractExpiryReminderRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.ContractReminderSchedulerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ContractReminderSchedulerImplTest {

    @Mock
    private ContractExpiryReminderRepository reminderRepository;

    @InjectMocks
    private ContractReminderSchedulerImpl reminderScheduler;

    private ContractExpiryReminder reminder;
    private Contract contract;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contract = new Contract();
        contract.setId(1L);
        contract.setVendorId(101L);
        contract.setEndDate(LocalDate.of(2025, 9, 15));

        reminder = new ContractExpiryReminder();
        reminder.setId(1L);
        reminder.setContract(contract);
        reminder.setReminderDate(LocalDate.now());
        reminder.setReminderSent(false);
    }

    @Test
    void testSendReminders() {
        List<ContractExpiryReminder> reminders = List.of(reminder);

        when(reminderRepository.findByReminderDateAndReminderSent(LocalDate.now(), false))
                .thenReturn(reminders);

        reminderScheduler.sendReminders();

        assertTrue(reminder.isReminderSent());
        verify(reminderRepository, times(1)).save(reminder);
    }
}

