package dev.gray.api;
/* Author: Grayson Howard
 * Modified: 04/22/2022
 * Handles action requests and take appropriate action
 */

import com.google.gson.Gson;
import dev.gray.data.EmployeeDAOPostgres;
import dev.gray.data.ExpenseDAOPostgres;
import dev.gray.entities.*;
import dev.gray.services.*;

import io.javalin.Javalin;

import java.util.List;
/*
 *  TODO: surround gson requests in try catch to handle bad formatting
 *  TODO: Create second logger to print only errors to console and send Main to a file
 */

public class ReimbursementApp {

    static Gson gson = new Gson();
    static String employee404 = "No Employee Found";
    static String expense404 = "No Expense Found";

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        ReimbursementService rs = new ReimbursementServiceImpl(new EmployeeDAOPostgres(), new ExpenseDAOPostgres());

        // Server health check route
        app.get("/", context -> {
            context.status(201);
        });
        /*
         * Employee Routes
         */
        // Create new employee
        app.post("/employees", context -> {
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e = rs.newEmployee(e);
            context.status((e != null)?201:500);
            context.result((e != null)?"Employee Created!" : "Failed to Create Employee");
        });
        // Fetch all employees
        app.get("/employees", context -> {
            List<Employee> employees = rs.employees();
            String json = gson.toJson(employees);
            context.result(json);
        });
        // Fetch employee where id == {id}
        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            String json = gson.toJson(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?json: employee404);
        });
        // Update employee where id == {id}
        app.put("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e.setId(id);
            e = rs.updateEmployee(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?"Employee Updated": employee404);
        });
        // Delete employee where id == {id}
        app.delete("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            if(e != null){
                boolean res = rs.removeEmployee(e);
                context.status((res)?201:500);
                context.result((res)?"Employee Removed":"Employee Not Removed");
            }else{
                context.status(404);
                context.result(employee404);
            }
        });
        /*
         * Expense Routes
         */
        // Create new Expense
        app.post("/expenses", context -> {
            String body = context.body();
            Expense ex = gson.fromJson(body, Expense.class);
            ex = rs.newExpense(ex);
            context.status((ex != null)?201:500);
            context.result((ex != null)?"Expense added!" : "Failed to add expense");
        });
        // Fetch all expenses
        app.get("/expenses", context -> {
            List<Expense> expenses = rs.expenses();
            String json = gson.toJson(expenses);
            context.result(json);
        });
        // Fetch expense where ExpId == {id}
        app.get("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Expense ex = rs.expense(id);
            String json = gson.toJson(ex);
            context.status((ex!=null)?201:404);
            context.result((ex!=null)?json: expense404);
        });
        // Update expense where ExpId == {id}
        app.put("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            // Does it exist?
            if(rs.expense(id) == null) { // It does not return client error
                context.status(404);
                context.result(expense404);
            }else{ // It does try to update
                String body = context.body();
                Expense ex = gson.fromJson(body, Expense.class);
                if(ex.getExpId() != id){
                    context.status(400);
                    context.result("Route ID does not match ID found in body.");
                }else if(rs.updateExpense(ex)){ // Try to update
                    context.status(201);
                    context.result("Expense Updated!");
                }else{ // Failed to update
                    context.status(400);
                    context.result("Only PENDING requests my be updated");
                }
            }
        });
        // Approve expense where ExpId == {id}
        app.patch("/expenses/{id}/approve", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Expense ex = rs.expense(id);
            // Does it exist?
            if(ex == null) { // It does not return client error
                context.status(404);
                context.result(expense404);
            }else{ // It does try to approve
                // Can it be approved?
                if(rs.expenseStatus(ex, Status.APPROVED)){ // Yes
                    context.status(201);
                    context.result("Expense Approved!");
                }else{ // No
                    context.status(400);
                    context.result("Only PENDING requests my be approved");
                }
            }
        });
        // Deny expense where ExpId == {id}
        app.patch("/expenses/{id}/deny", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Expense ex = rs.expense(id);
            // Does it exist?
            if(ex == null) { // It does not return client error
                context.status(404);
                context.result(expense404);
            }else{ // It does try to update
                // Can it be denied?
                if(rs.expenseStatus(ex, Status.DENIED)){ // Yes
                    context.status(201);
                    context.result("Expense denied!");
                }else{ // No
                    context.status(400);
                    context.result("Only PENDING requests my be denied");
                }
            }
        });
        // Delete expense where ExpId == {id}
        app.delete("/expenses/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            // Does it exist?
            if(rs.expense(id) == null) { // It does not return client error
                context.status(404);
                context.result(expense404);
            }else{ // It does try to delete
                if(rs.deleteExpense(rs.expense(id))){ // Yes
                    context.status(201);
                    context.result("Expense deleted!");
                }else{ // No
                    context.status(400);
                    context.result("Only PENDING requests my be deleted");
                }
            }
        });
        /*
         * Nested Routes
         */
        // Fetch expenses for an employee where id == {id}
        app.get("/employees/{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            if(e != null) {
                List<Expense> expenses = rs.expenses(e);
                String json = gson.toJson(expenses);
                context.status(201);
                context.result(json);
            }else{
                context.status(404);
                context.result(employee404);
            }
        });
        app.put("/employees/{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            // Does the employee exist?
            if(e == null) { // It does not return client error
                context.status(404);
                context.result(employee404);
            }else{ // Employee exists and we should try to add the expense
                String body = context.body();
                Expense ex = gson.fromJson(body, Expense.class);
                if(ex.getEmplId() != id){
                    context.status(400);
                    context.result("Route ID does not match ID found in body.");
                }else{ // Failed to update
                    ex = rs.newExpense(ex);
                    context.status((ex != null)?201:500);
                    context.result((ex != null)?"Expense added!" : "Failed to add expense");
                }
            }
        });
        app.start(5000);
    }
}
