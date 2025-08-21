package com.capstone.Third_Party_Vendor_Management_System.service.Impl;

import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        logger.info("Creating new employee with username: {}", employee.getUsername());
        String hashedPassword = passwordEncoder.encode(employee.getPasswordHash());
        employee.setPasswordHash(hashedPassword);
        Employee saved = employeeRepository.save(employee);
        logger.info("Employee created with ID: {}", saved.getId());
        return saved;
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, Employee updatedEmployee) {
        logger.info("Updating employee with ID: {}", id);
        return employeeRepository.findById(id).map(employee -> {
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setUsername(updatedEmployee.getUsername());
            String hashedPassword = passwordEncoder.encode(updatedEmployee.getPasswordHash());
            employee.setPasswordHash(hashedPassword);
            Employee updated = employeeRepository.save(employee);
            logger.info("Employee updated with ID: {}", updated.getId());
            return updated;
        });
    }

    @Override
    public boolean deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with ID: {}", id);
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            logger.info("Employee deleted with ID: {}", id);
            return true;
        }
        logger.warn("Employee with ID {} not found for deletion", id);
        return false;
    }
}
