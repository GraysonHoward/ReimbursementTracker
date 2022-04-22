package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Defines actions that are legal to take
 * when interacting with the server
 */
import dev.gray.entities.Employee;
import dev.gray.entities.Expense;
import dev.gray.entities.Status;

import java.util.List;

public interface ReimbursementService {
    /*
     * Employee REST route related methods
     */
    // Enter new Employee
    Employee newEmployee(Employee e);
    // List all Employees
    List<Employee> employees();
    // Get a specific Employee
    Employee fetchEmployee(int id);
    // Update an employee
    Employee updateEmployee(Employee e);
    // Removes an employee
    boolean removeEmployee(Employee e);
    /*
     * Expense REST route related methods
     */
    // Enter new Expense amount must be positive
    Expense newExpense(Expense ex);
    // Return all Expenses!!!
    List<Expense> expenses();
    // Return expenses with specified status
    List<Expense> expenses(Status status);
    // Return specific expense
    Expense expense(int id);
    // Update specific expense IFF status is PENDING
    boolean expense(Expense ex);
    // Change Status of an expense IFF status is PENDING
    boolean expenseStatus(Expense ex, Status status);
    // Delete an expense IFF status is PENDING
    boolean deleteExpense(Expense ex);
    /*
     * Nested REST route related methods
     */
    // Get all expenses for specified employee
    List<Expense> expenses(Employee e);
}
