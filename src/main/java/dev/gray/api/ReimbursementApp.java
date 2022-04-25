package dev.gray.api;
/* Author: Grayson Howard
 * Modified: 04/22/2022
 * Handles action requests and take appropriate action
 */

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.gray.data.EmployeeDAOPostgres;
import dev.gray.data.ExpenseDAOPostgres;
import dev.gray.entities.*;
import dev.gray.services.*;

import io.javalin.Javalin;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class ReimbursementApp {
    static Logger log = Logger.getLogger("ReimbursementApp");
    static Gson gson = new Gson();
    static String employee404 = "No Employee Found";
    static String expense404 = "No Expense Found";
    static String jsonFailure = "Unable to read Json from body";
    static String badId = "Route ID does not match ID found in body";

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
            try{
                String body = context.body();
                Employee e = gson.fromJson(body, Employee.class);
                e = rs.newEmployee(e);
                context.status((e != null)?201:500);
                context.result((e != null)?"Employee Created!" : "Failed to Create Employee");
            }catch(JsonSyntaxException exc){
                log.error(jsonFailure);
                context.status(400);
                context.result(jsonFailure);
            }
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
            try{
                int id = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Employee update = gson.fromJson(body, Employee.class);
                if(rs.fetchEmployee(id) != null) { // Employee for id exists
                    if(update.getId() == id || update.getId() == 0) { // Id either matches or not provided
                        update = rs.updateEmployee(update, id);
                        context.status((update != null) ? 201 : 404);
                        context.result((update != null) ? "Employee Updated" : employee404);
                    }else{
                        log.info(badId);
                        context.status(400);
                        context.result(badId);
                    }
                }else{
                    log.info(employee404);
                    context.status(404);
                    context.result(employee404);
                }
            }catch(JsonSyntaxException exc){
                log.error(jsonFailure);
                context.status(400);
                context.result(jsonFailure);
            }
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
                log.info(employee404);
                context.status(404);
                context.result(employee404);
            }
        });
        /*
         * Expense Routes
         */
        // Create new Expense
        app.post("/expenses", context -> {
            try{
                String body = context.body();
                Expense ex = gson.fromJson(body, Expense.class);
                ex = rs.newExpense(ex);
                context.status((ex != null)?201:500);
                context.result((ex != null)?"Expense added!" : "Failed to add expense");
            }catch(JsonSyntaxException exc){
                log.error(jsonFailure);
                context.status(400);
                context.result(jsonFailure);
            }
        });
        // Fetch all expenses
        app.get("/expenses", context -> {
            List<Expense> expenses;
            String param = context.queryParam("status");
            if(param != null){// We should only return expenses with a specific status
                Status stat = Status.valueOf(param.toUpperCase());
                expenses = rs.expenses(stat);
                String json = gson.toJson(expenses);
                context.status(201);
                context.result(json);
            }else { // We return all expenses
                expenses = rs.expenses();
                String json = gson.toJson(expenses);
                context.status(201);
                context.result(json);
            }
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
            try {
                int id = Integer.parseInt(context.pathParam("id"));
                String body = context.body();
                Expense update = gson.fromJson(body, Expense.class);
                if(rs.expense(id) != null){
                    if(update.getExpId() == id || update.getExpId() == 0){
                        if(rs.updateExpense(update, id)){
                            context.status(201);
                            context.result("Expense Updated!");
                        }else{
                            context.status(400);
                            context.result("Update failed. Only PENDING requests may be updated.");
                        }
                    }else{
                        log.info(badId);
                        context.status(400);
                        context.result(badId);
                    }
                }else{
                    log.info(expense404);
                    context.status(404);
                    context.result(expense404);
                }
            }catch(JsonSyntaxException exc){
                log.error(jsonFailure);
                context.status(400);
                context.result(jsonFailure);
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
        app.post("/employees/{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            // Does the employee exist?
            if(e == null) { // It does not return client error
                context.status(404);
                context.result(employee404);
            }else{ // Employee exists and we should try to add the expense
                try{
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
                }catch(JsonSyntaxException exc){
                    log.error(jsonFailure);
                    context.status(400);
                    context.result(jsonFailure);
                }
            }
        });
        app.start(5000);
    }
}
