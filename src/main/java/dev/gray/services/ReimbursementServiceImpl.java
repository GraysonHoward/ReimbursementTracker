package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/20/2022
 * Implementation
 */

import dev.gray.data.EmployeeDAO;
import dev.gray.data.ExpenseDAO;
import dev.gray.entities.Employee;
import dev.gray.entities.Expense;
import dev.gray.entities.Status;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService {

    private final EmployeeDAO employeeDAO;
    private final ExpenseDAO expenseDAO;

    public ReimbursementServiceImpl(EmployeeDAO employeeDAO, ExpenseDAO expenseDAO){
        this.employeeDAO = employeeDAO;
        this.expenseDAO = expenseDAO;
    }

    @Override
    public Employee newEmployee(Employee e) {
        return employeeDAO.createNew(e);
    }

    @Override
    public List<Employee> employees() {
        return employeeDAO.getAllEmployee();
    }

    @Override
    public Employee fetchEmployee(int id) {
        return employeeDAO.getByID(id);
    }

    @Override
    public Employee updateEmployee(Employee e) {
        return employeeDAO.updateEmployee(e);
    }

    @Override
    public boolean removeEmployee(Employee e) {
        return employeeDAO.deleteEmployee(e);
    }

    @Override
    public Expense newExpense(Expense ex) {
        return null;
    }

    @Override
    public List<Expense> expenses() {
        return null;
    }

    @Override
    public List<Expense> expenses(Status status) {
        return null;
    }

    @Override
    public Expense expense(int id) {
        return null;
    }

    @Override
    public boolean expense(Expense ex) {
        return false;
    }

    @Override
    public boolean expenseStatus(Expense ex, Status status) {
        return false;
    }

    @Override
    public boolean deleteExpense(Expense ex) {
        return false;
    }

    @Override
    public List<Expense> expenses(Employee e) {
        return null;
    }
}
