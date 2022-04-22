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
        app.post("/employees", context -> {
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e = rs.newEmployee(e);
            context.status((e != null)?201:500);
            context.result((e != null)?"Employee Created!" : "Failed to Create Employee");
        });
        app.get("/employees", context -> {
            List<Employee> employees = rs.employees();
            String json = gson.toJson(employees);
            context.result(json);
        });
        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            String json = gson.toJson(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?json: employee404);
        });
        // What is this supposed to be doing differently
        app.put("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e.setId(id);
            e = rs.updateEmployee(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?"Employee Updated": employee404);
        });
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
        app.post("/expenses", context -> {

        });
        app.get("/expenses", context -> {

        });
        app.get("/expenses/{id}", context -> {

        });
        app.put("/expenses/{id}", context -> {

        });
        app.patch("/expenses/{id}/approve", context -> {

        });
        app.patch("/expenses/{id}/deny", context -> {

        });
        app.delete("/expenses/{id}", context -> {

        });
        /*
         * Nested Routes
         */
        app.get("/employees/{id}/expenses", context -> {

        });
        app.put("/employees/{id}/expenses", context -> {

        });
        app.start(5000);
    }
}
