package com.capstone.Third_Party_Vendor_Management_System.service;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeService {


    Page<Employee> getAllEmployees(Pageable pageable);


    Optional<Employee> getEmployeeById(Long id);

    Employee createEmployee(Employee employee);

    Optional<Employee> updateEmployee(Long id, Employee updatedEmployee);

    boolean deleteEmployee(Long id);
}
