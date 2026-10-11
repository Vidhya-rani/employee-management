package com.example.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.employee.dto.EmployeeRequestDTO;
import com.example.employee.dto.EmployeeResponseDTO;
import com.example.employee.entity.Employee;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;


    // CREATE
    @Test
    void shouldCreateEmployee() {

        EmployeeRequestDTO request = new EmployeeRequestDTO();

        request.setName("Rahul");
        request.setEmail("rahul@gmail.com");
        request.setDepartment("IT");
        request.setSalary(60000);

        Employee savedEmployee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(savedEmployee);

        EmployeeResponseDTO response =
                employeeService.saveEmployee(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("IT", response.getDepartment());
        assertEquals(60000, response.getSalary());

        verify(employeeRepository, times(1))
                .save(any(Employee.class));
    }


    // GET BY ID
    @Test
    void shouldGetEmployeeById() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        EmployeeResponseDTO response =
                employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Rahul", response.getName());
        assertEquals("IT", response.getDepartment());

        verify(employeeRepository, times(1))
                .findById(1L);
    }


    // NOT FOUND
    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(999L)
        );

        verify(employeeRepository, times(1))
                .findById(999L);
    }


    // UPDATE
    @Test
    void shouldUpdateEmployee() {

        Employee existingEmployee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        EmployeeRequestDTO request = new EmployeeRequestDTO();

        request.setName("Rahul Kumar");
        request.setEmail("rahulkumar@gmail.com");
        request.setDepartment("IT");
        request.setSalary(70000);

        Employee updatedEmployee = new Employee(
                1L,
                "Rahul Kumar",
                "rahulkumar@gmail.com",
                "IT",
                70000
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(existingEmployee));

        when(employeeRepository.save(existingEmployee))
                .thenReturn(updatedEmployee);

        EmployeeResponseDTO response =
                employeeService.updateEmployee(1L, request);

        assertEquals("Rahul Kumar", response.getName());
        assertEquals("rahulkumar@gmail.com", response.getEmail());
        assertEquals(70000, response.getSalary());

        verify(employeeRepository, times(1))
                .findById(1L);

        verify(employeeRepository, times(1))
                .save(existingEmployee);
    }


    // DELETE
    @Test
    void shouldDeleteEmployee() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1))
                .findById(1L);

        verify(employeeRepository, times(1))
                .delete(employee);
    }


    // SEARCH BY DEPARTMENT
    @Test
    void shouldFindEmployeesByDepartment() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.findByDepartmentIgnoreCase("IT"))
                .thenReturn(List.of(employee));

        List<EmployeeResponseDTO> response =
                employeeService.getEmployeesByDepartment("IT");

        assertEquals(1, response.size());
        assertEquals("Rahul", response.get(0).getName());
        assertEquals("IT", response.get(0).getDepartment());

        verify(employeeRepository, times(1))
                .findByDepartmentIgnoreCase("IT");
    }


    // SEARCH BY SALARY
    @Test
    void shouldFindEmployeesBySalary() {

        Employee employee = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.findBySalaryGreaterThan(50000))
                .thenReturn(List.of(employee));

        List<EmployeeResponseDTO> response =
                employeeService.getEmployeesBySalaryGreaterThan(50000);

        assertEquals(1, response.size());
        assertEquals(60000, response.get(0).getSalary());

        verify(employeeRepository, times(1))
                .findBySalaryGreaterThan(50000);
    }


    // SEARCH BY NAME
    @Test
    void shouldSearchEmployeesByName() {

        Employee employee = new Employee(
                1L,
                "Rahul Kumar",
                "rahulkumar@gmail.com",
                "IT",
                60000
        );

        when(employeeRepository.findByNameContainingIgnoreCase("Rahul"))
                .thenReturn(List.of(employee));

        List<EmployeeResponseDTO> response =
                employeeService.searchEmployeesByName("Rahul");

        assertEquals(1, response.size());
        assertEquals(
                "Rahul Kumar",
                response.get(0).getName()
        );

        verify(employeeRepository, times(1))
                .findByNameContainingIgnoreCase("Rahul");
    }


    // PAGINATION + SORTING
    @Test
    void shouldGetEmployeesWithPaginationAndSorting() {

        Employee employee1 = new Employee(
                1L,
                "Rahul",
                "rahul@gmail.com",
                "IT",
                70000
        );

        Employee employee2 = new Employee(
                2L,
                "Sneha",
                "sneha@gmail.com",
                "HR",
                50000
        );

        Page<Employee> employeePage =
                new PageImpl<>(
                        List.of(employee1, employee2),
                        PageRequest.of(0, 2),
                        2
                );

        when(employeeRepository.findAll(any(PageRequest.class)))
                .thenReturn(employeePage);

        Page<EmployeeResponseDTO> response =
                employeeService.getAllEmployees(
                        0,
                        2,
                        "salary",
                        "desc"
                );

        assertNotNull(response);
        assertEquals(2, response.getContent().size());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        assertEquals(
                "Rahul",
                response.getContent().get(0).getName()
        );

        assertEquals(
                70000,
                response.getContent().get(0).getSalary()
        );

        verify(employeeRepository, times(1))
                .findAll(any(PageRequest.class));
    }
}