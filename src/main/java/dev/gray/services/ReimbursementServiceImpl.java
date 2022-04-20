package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/20/2022
 * Implementation
 */

import dev.gray.data.EmployeeDAO;
import dev.gray.data.ExpenseDAO;
import dev.gray.entities.Employee;

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
    public List<Employee> listEmployees() {
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
}
