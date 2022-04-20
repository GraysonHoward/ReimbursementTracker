package dev.gray.data;

import dev.gray.entities.Employee;
import dev.gray.entities.Expense;

import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {
    @Override
    public Expense createExpense(Expense ex) {
        return null;
    }

    @Override
    public Expense getExpenseByID(int id) {
        return null;
    }

    @Override
    public List<Expense> expenses(Employee e) {
        return null;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return null;
    }

    @Override
    public Expense updateExpense(Expense ex) {
        return null;
    }

    @Override
    public boolean deleteExpense(Expense ex) {
        return false;
    }
}
