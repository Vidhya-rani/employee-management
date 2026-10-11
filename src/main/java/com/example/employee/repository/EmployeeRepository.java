package com.example.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findBySalaryGreaterThan(double salary);

    List<Employee> findByNameContainingIgnoreCase(String name);
}