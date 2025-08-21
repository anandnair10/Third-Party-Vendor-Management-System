package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;
import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.ContractReminderSchedulerImpl;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractServiceImplTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ContractReminderSchedulerImpl reminderScheduler;

    @InjectMocks
    private ContractServiceImpl contractService;

    private Contract contract;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contract = new Contract();
        contract.setId(1L);
        contract.setContractDetails("Annual IT Support");
        contract.setStartDate(LocalDate.of(2024, 1, 1));
        contract.setEndDate(LocalDate.of(2025, 1, 1));
        contract.setContractValue(1000000.00);
    }

    @Test
    void testCreateContract() {
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        Contract result = contractService.createContract(contract);

        assertNotNull(result);
        assertEquals("Annual IT Support", result.getContractDetails());
        verify(reminderScheduler, times(1)).sendReminders();
        assertNotNull(result.getContractExpiryReminder());
        assertEquals(LocalDate.of(2024, 12, 2), result.getContractExpiryReminder().getReminderDate());
    }

    @Test
    void testGetAllContracts() {
        List<Contract> contracts = List.of(contract);
        when(contractRepository.findAll()).thenReturn(contracts);

        List<Contract> result = contractService.getAllContracts();

        assertEquals(1, result.size());
        assertEquals(contract, result.get(0));
    }

    @Test
    void testGetContractById_Success() {
        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));

        Contract result = contractService.getContractById(1L);

        assertEquals(contract, result);
    }

    @Test
    void testGetContractById_NotFound() {
        when(contractRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> contractService.getContractById(1L));
        assertEquals("Contract not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateContract() {
        Contract updated = new Contract();
        updated.setContractDetails("Updated Contract");
        updated.setStartDate(LocalDate.of(2024, 2, 1));
        updated.setEndDate(LocalDate.of(2025, 2, 1));
        updated.setContractValue(1200000.00);

        ContractExpiryReminder reminder = new ContractExpiryReminder();
        reminder.setReminderDate(LocalDate.of(2024, 12, 31));
        reminder.setReminderSent(true);
        contract.setContractExpiryReminder(reminder);

        when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
        when(contractRepository.save(any(Contract.class))).thenReturn(contract);

        Contract result = contractService.updateContract(1L, updated);

        assertEquals("Updated Contract", result.getContractDetails());
        assertEquals(LocalDate.of(2025, 2, 1), result.getEndDate());
        assertEquals(LocalDate.of(2025, 1, 2), result.getContractExpiryReminder().getReminderDate());
        assertFalse(result.getContractExpiryReminder().isReminderSent());
    }

    @Test
    void testDeleteContract() {
        doNothing().when(contractRepository).deleteById(1L);

        contractService.deleteContract(1L);

        verify(contractRepository, times(1)).deleteById(1L);
    }
}

