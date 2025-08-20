package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Contract;

import java.util.List;

public interface ContractService {

    Contract createContract(Contract contract);

    List<Contract> getAllContracts();

    Contract getContractById(Long id);

    Contract updateContract(Long id, Contract updatedContract);

    void deleteContract(Long id);
}

