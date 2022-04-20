package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Defines actions that are legal to take
 * when interacting with the server
 */
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
