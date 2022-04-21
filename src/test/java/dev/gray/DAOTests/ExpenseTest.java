package dev.gray.DAOTests;
/* Author: Grayson Howard
 * Modified: 04/21/2022
 * CRUD tests for the EmployeeDAO
 */

import dev.gray.data.EmployeeDAO;
import dev.gray.data.EmployeeDAOPostgres;
import dev.gray.data.ExpenseDAO;
import dev.gray.data.ExpenseDAOPostgres;
import dev.gray.entities.*;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order
class ExpenseTest {
    static EmployeeDAO eDAO = new EmployeeDAOPostgres();
    static ExpenseDAO exDAO = new ExpenseDAOPostgres();
    static Employee eTest;
    static Employee eTest2;
    static Expense exTest;

    @Test
    @Order(1)
    void create_expense(){
        eTest = new Employee(0, "Banana", "Man");
        eTest = eDAO.createNew(eTest);
        exTest = new Expense(0,eTest.getId(),50.00, null);
        exTest = exDAO.createExpense(exTest);
        Assertions.assertNotEquals(0, exTest.getExpId());
        Assertions.assertEquals(Status.PENDING, exTest.getStat());
    }

    @Test
    @Order(2)
    void retrieve_expense(){
        Expense retrieved = exDAO.getExpenseByID(exTest.getExpId());
        Assertions.assertEquals(retrieved.getExpId(), exTest.getExpId());
    }

    @Test
    @Order(3)
    void retrieve_all_expense_for_one(){
        // Give Banana Man a few more expenses
        Expense ex1 = new Expense(0,eTest.getId(),60.00, null);
        Expense ex2 = new Expense(0,eTest.getId(),10.00, null);
        exDAO.createExpense(ex1);
        exDAO.createExpense(ex2);

        // Make sure they all show up
        List<Expense> retrieved = exDAO.expenses(eTest.getId());
        Assertions.assertEquals(3, retrieved.size());
    }
    @Test
    @Order(4)
    void retrieve_all_expense(){
        // Add another user and Expense
        eTest2 = new Employee(0, "Apple", "Man");
        eTest2 = eDAO.createNew(eTest2);
        Expense ex1 = new Expense(0, eTest.getId(),60.00, null);
        exDAO.createExpense(ex1);

        // Make sure they all show up
        List<Expense> retrieved = exDAO.expenses();
        Assertions.assertTrue(retrieved.size()>=4);
    }

    @Test
    @Order(5)
    void update_expense(){
        exTest.setStat(Status.APPROVED);
        Expense retrieved = exDAO.updateExpense(exTest);
        Assertions.assertEquals(Status.APPROVED,exTest.getStat());
    }

    @Test
    @Order(6)
    //Make sure expenses are deleted when an employee is
    void cascade_on_delete(){
        int id = eTest2.getId();
        eDAO.deleteEmployee(eTest2);
        List<Expense> empty = exDAO.expenses(id);
        Assertions.assertEquals(0,empty.size());
    }

    @Test
    @Order(7)
    void delete_expense(){
        List<Expense> expenses = exDAO.expenses(eTest.getId());
        expenses.removeIf(e -> exDAO.deleteExpense(e));
        //list should now be empty
        Assertions.assertEquals(0,expenses.size());
        eDAO.deleteEmployee(eTest);
    }
}
