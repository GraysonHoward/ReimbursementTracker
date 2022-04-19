package dev.gray.services;

import dev.gray.entities.Employee;

import java.util.List;

public interface ReimbursementService {
    // Enter new Employee
    Employee newEmployee(Employee e);
    // List all Employees
    List<Employee> listEmployees();
    // Get a specific Employee
    Employee fetchEmployee(int id);
    // Update an employee
    Employee updateEmployee(Employee e);
    // Removes an employee
    boolean removeEmployee(Employee e);
}
