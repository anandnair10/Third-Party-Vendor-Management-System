package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;
import com.capstone.Third_Party_Vendor_Management_System.entities.ContractExpiryReminder;
import com.capstone.Third_Party_Vendor_Management_System.repository.ContractRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;
    private final ContractReminderSchedulerImpl reminderScheduler;

    public Contract createContract(Contract contract) {
        logger.info("Creating new contract with details: {}", contract.getContractDetails());

        ContractExpiryReminder reminder = ContractExpiryReminder.builder()
                .contract(contract)
                .reminderDate(contract.getEndDate().minusDays(30))
                .reminderSent(false)
                .build();

        contract.setContractExpiryReminder(reminder);

        logger.debug("Reminder set for contract: {} on date: {}", contract.getContractDetails(), reminder.getReminderDate());

        reminderScheduler.sendReminders();

        Contract savedContract = contractRepository.save(contract);
        logger.info("Contract saved with ID: {}", savedContract.getId());

        return savedContract;
    }

    public List<Contract> getAllContracts() {
        logger.info("Fetching all contracts");
        return contractRepository.findAll();
    }

    public Contract getContractById(Long id) {
        logger.info("Fetching contract with ID: {}", id);
        return contractRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Contract not found with ID: {}", id);
                    return new RuntimeException("Contract not found with ID: " + id);
                });
    }

    public Contract updateContract(Long id, Contract updatedContract) {
        logger.info("Updating contract with ID: {}", id);

        Contract existing = getContractById(id);
        existing.setContractDetails(updatedContract.getContractDetails());
        existing.setStartDate(updatedContract.getStartDate());
        existing.setEndDate(updatedContract.getEndDate());
        existing.setContractValue(updatedContract.getContractValue());

        ContractExpiryReminder reminder = existing.getContractExpiryReminder();
        if (reminder != null) {
            reminder.setReminderDate(updatedContract.getEndDate().minusDays(30));
            reminder.setReminderSent(false);
            logger.debug("Updated reminder date for contract ID {}: {}", id, reminder.getReminderDate());
        }

        Contract saved = contractRepository.save(existing);
        logger.info("Contract updated and saved with ID: {}", saved.getId());

        return saved;
    }

    public void deleteContract(Long id) {
        logger.info("Deleting contract with ID: {}", id);
        contractRepository.deleteById(id);
        logger.info("Contract deleted with ID: {}", id);
    }
}
