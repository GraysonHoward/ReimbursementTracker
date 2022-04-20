package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Class designed to interact with the Employee
 * table in the database
 */

import dev.gray.entities.Employee;
import dev.gray.entities.Expense;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeDAO {
    // Creates a new employee
    Employee createNew(Employee e);
    // Gets employee from database by ID
    Employee getByID(int id);
    // Gets all registered employees
    List<Employee> getAllEmployee();
    // Update an Employees information
    Employee updateEmployee(Employee e);
    //Remove an employee from the system
    boolean deleteEmployee(Employee e);
}
