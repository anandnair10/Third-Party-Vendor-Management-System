package com.capstone.Third_Party_Vendor_Management_System.service.Impl;


import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;
import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractReminderSchedulerImpl reminderScheduler;

    public Contract createContract(Contract contract) {
        ContractExpiryReminder reminder = ContractExpiryReminder.builder()
                .contract(contract)
                .reminderDate(contract.getEndDate().minusDays(30))
                .reminderSent(false)
                .build();

        contract.setContractExpiryReminder(reminder);

        System.out.println("Reminder set for contract: " + contract.getContractDetails() +
                " on date: " + reminder.getReminderDate());

        reminderScheduler.sendReminders();

        return contractRepository.save(contract);
    }
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found with ID: " + id));
    }

    public Contract updateContract(Long id, Contract updatedContract) {
        Contract existing = getContractById(id);
        existing.setContractDetails(updatedContract.getContractDetails());
        existing.setStartDate(updatedContract.getStartDate());
        existing.setEndDate(updatedContract.getEndDate());
        existing.setContractValue(updatedContract.getContractValue());

        // Update reminder
        ContractExpiryReminder reminder = existing.getContractExpiryReminder();
        if (reminder != null) {
            reminder.setReminderDate(updatedContract.getEndDate().minusDays(30));
            reminder.setReminderSent(false);
        }

        return contractRepository.save(existing);
    }

    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }

}

