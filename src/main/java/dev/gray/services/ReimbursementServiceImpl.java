package dev.gray.services;
/* Author: Grayson Howard
 * Modified: 04/22/2022
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
    public Employee updateEmployee(Employee e, int id) {
        Employee old = employeeDAO.getByID(id);
        // The Employee e that is passed may be incomplete
        // Retrieve old info if noe is provided
        if(e.getId()==0)
            e.setId(old.getId());
        if(e.getFName() == null)
            e.setFName(old.getFName());
        if(e.getLName() == null)
            e.setLName(old.getLName());
        return employeeDAO.updateEmployee(e);
    }

    @Override
    public boolean removeEmployee(Employee e) {
        return employeeDAO.deleteEmployee(e);
    }

    @Override
    // Create new expense IFF the amount is negative
    public Expense newExpense(Expense ex) {
        if(ex.getAmount()>0)
            return expenseDAO.createExpense(ex);
        return null;
    }

    @Override
    public List<Expense> expenses() {
        return expenseDAO.expenses();
    }

    @Override
    public List<Expense> expenses(Status status) {
        return expenseDAO.expenses(status);
    }

    @Override
    public Expense expense(int id) {
        return expenseDAO.getExpenseByID(id);
    }

    @Override
    public boolean updateExpense(Expense ex, int id) {
        // Fill in any blanks
        Expense old = expenseDAO.getExpenseByID(id);
        if(ex.getExpId() == 0)
            ex.setExpId(old.getExpId());
        if(ex.getEmplId() == 0)
            ex.setEmplId(old.getEmplId());
        if(ex.getAmount() == -1 )
            ex.setAmount(old.getAmount());
        if(ex.getStat() == null)
            ex.setStat(old.getStat());
        // Otherwise meets conditions
        if(ex.getStat()==Status.PENDING && ex.getAmount() > 0){
            expenseDAO.updateExpense(ex);
            return true;
        }
        return false;
    }

    @Override
    public boolean expenseStatus(Expense ex, Status status) {
        if(ex.getStat()==Status.PENDING){
            ex.setStat(status);
            expenseDAO.updateExpense(ex);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExpense(Expense ex) {
        if(ex.getStat()==Status.PENDING){
            return expenseDAO.deleteExpense(ex);
        }
        return false;
    }

    @Override
    public List<Expense> expenses(Employee e) {
        return expenseDAO.expenses(e.getId());
    }
}
