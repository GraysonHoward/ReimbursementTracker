package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/21/2022
 * Class designed to interact with the Expense
 * table in the database
 */
import dev.gray.entities.Expense;
import dev.gray.entities.Status;

import java.util.List;

public interface ExpenseDAO {
    // Log a new expense
    Expense createExpense(Expense ex);
    // Get an Expense by its ID
    Expense getExpenseByID(int id);
    // Get all logged expenses if not provided with an employee
    List<Expense> expenses();
    // Gets all expenses for an employee
    List<Expense> expenses(int id);
    // Gets all expenses with a specified status
    List<Expense> expenses(Status stat);
    // Update and expense
    Expense updateExpense(Expense ex);
    // Delete an Expense this can only be done to pending expenses
    boolean deleteExpense(Expense ex);
}