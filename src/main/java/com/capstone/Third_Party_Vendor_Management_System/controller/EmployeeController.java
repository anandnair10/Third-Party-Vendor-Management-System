package com.capstone.Third_Party_Vendor_Management_System.controller;

import com.capstone.Third_Party_Vendor_Management_System.dto.EmployeeDTO;
import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.mapper.EmployeeMapper;
import com.capstone.Third_Party_Vendor_Management_System.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //Get all employees

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<EmployeeDTO>> getPaginatedEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employeePage = employeeService.getAllEmployees(pageable)
                .map(EmployeeMapper::toDTO);

        return ResponseEntity.ok(employeePage);
    }



    // Get employee by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getEmployeeById/{id}")

    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
        return employeeOptional
                .map(employee -> ResponseEntity.ok(EmployeeMapper.toDTO(employee)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    
    //Create new employee
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/createEmployee")
    
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // Update employee
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/updateEmployee/{id}")

    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> updated = employeeService.updateEmployee(id, updatedEmployee);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    //  Delete employee

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteEmployee/{id}")

    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
