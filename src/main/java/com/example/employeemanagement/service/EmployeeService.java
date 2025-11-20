package com.example.employeemanagement.service;

import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.exception.ResourceNotFoundException;
import com.example.employeemanagement.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    public Employee update(Long id, Employee updated) {
        Employee existing = findById(id);
        existing.setName(updated.getName());
        existing.setPosition(updated.getPosition());
        existing.setDepartment(updated.getDepartment());
        existing.setHireDate(updated.getHireDate());
        return employeeRepository.save(existing);
    }

    public void delete(Long id) {
        Employee existing = findById(id);
        employeeRepository.delete(existing);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}