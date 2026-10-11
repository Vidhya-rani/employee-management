package com.example.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO request) {

        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToResponseDTO(savedEmployee);
    }

    // GET ALL - PAGINATION + SORTING
    public Page<EmployeeResponseDTO> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    // GET BY ID
    public EmployeeResponseDTO getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        return convertToResponseDTO(employee);
    }

    // UPDATE
    public EmployeeResponseDTO updateEmployee(
            Long id,
            EmployeeRequestDTO request) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        existingEmployee.setName(request.getName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setDepartment(request.getDepartment());
        existingEmployee.setSalary(request.getSalary());

        Employee updatedEmployee =
                employeeRepository.save(existingEmployee);

        return convertToResponseDTO(updatedEmployee);
    }

    // DELETE
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id: " + id));

        employeeRepository.delete(employee);
    }

    // SEARCH BY DEPARTMENT
    public List<EmployeeResponseDTO> getEmployeesByDepartment(
            String department) {

        return employeeRepository
                .findByDepartmentIgnoreCase(department)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // SEARCH BY SALARY
    public List<EmployeeResponseDTO> getEmployeesBySalaryGreaterThan(
            double salary) {

        return employeeRepository
                .findBySalaryGreaterThan(salary)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // SEARCH BY NAME
    public List<EmployeeResponseDTO> searchEmployeesByName(
            String name) {

        return employeeRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // ENTITY -> RESPONSE DTO
    private EmployeeResponseDTO convertToResponseDTO(
            Employee employee) {

        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }
}