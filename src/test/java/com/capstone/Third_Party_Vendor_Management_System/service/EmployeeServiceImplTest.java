package com.capstone.Third_Party_Vendor_Management_System.service;

import com.capstone.Third_Party_Vendor_Management_System.entities.Employee;
import com.capstone.Third_Party_Vendor_Management_System.repository.EmployeeRepository;
import com.capstone.Third_Party_Vendor_Management_System.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employee = new Employee();
        employee.setId(1L);
        employee.setUsername("empuser");
        employee.setPasswordHash("pass123");
        employee.setDepartment("IT");
    }

    @Test
    void testGetAllEmployeesPaginated() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Employee> employeeList = List.of(employee);
        Page<Employee> page = new PageImpl<>(employeeList, pageable, employeeList.size());

        when(employeeRepository.findAll(pageable)).thenReturn(page);

        Page<Employee> result = employeeService.getAllEmployees(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("empuser", result.getContent().get(0).getUsername());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
    }

    @Test
    void testCreateEmployee() {
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee saved = employeeService.createEmployee(employee);

        assertEquals(employee, saved);
    }

    @Test
    void testUpdateEmployee() {
        Employee updated = new Employee();
        updated.setUsername("updatedUser");
        updated.setPasswordHash("newPass");
        updated.setDepartment("HR");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);

        Optional<Employee> result = employeeService.updateEmployee(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("updatedUser", result.get().getUsername());
        assertEquals("HR", result.get().getDepartment());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        boolean result = employeeService.deleteEmployee(1L);

        assertTrue(result);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_Failure() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        boolean result = employeeService.deleteEmployee(1L);

        assertFalse(result);
    verify(employeeRepository, never()).deleteById(anyLong());
    }
}
