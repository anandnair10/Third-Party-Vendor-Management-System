package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;
import com.capstone.Third_Party_Vendor_Management_System.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // Create a new contract
    @PostMapping("/createContract")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract savedContract = contractService.createContract(contract);
        return ResponseEntity.ok(savedContract);
    }

    // Get all contracts
    @GetMapping("/getAllContracts")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    // Get contract by ID
    @GetMapping("/getContract/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        Contract contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }

    // Update contract
    @PutMapping("/updateContract/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody Contract updatedContract) {
        Contract contract = contractService.updateContract(id, updatedContract);
        return ResponseEntity.ok(contract);
    }

    // Delete contract
    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}

