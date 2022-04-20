package dev.gray.api;
/* Author: Grayson Howard
 * Modified: 04/20/2022
 * Handles action requests and take appropriate action
 */

import com.google.gson.Gson;
import dev.gray.data.EmployeeDAOPostgres;
import dev.gray.data.ExpenseDAOPostgres;
import dev.gray.entities.*;
import dev.gray.services.*;

import io.javalin.Javalin;
import org.apache.log4j.PropertyConfigurator;

import java.util.List;

public class ReimbursementApp {

    static Gson gson = new Gson();

    public static void main(String[] args) {
        Javalin app = Javalin.create();
        ReimbursementService rs = new ReimbursementServiceImpl(new EmployeeDAOPostgres(), new ExpenseDAOPostgres());

        // Server health check route
        app.get("/", context -> {
            context.status(201);
        });
        app.post("/employees", context -> {
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e = rs.newEmployee(e);
            context.status((e != null)?201:500);
            context.result((e != null)?"Employee Created!" : "Failed to Create Employee");
        });
        app.get("/employees", context -> {
            List<Employee> employees = rs.listEmployees();
            String json = gson.toJson(employees);
            context.result(json);
        });
        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            Employee e = rs.fetchEmployee(id);
            String json = gson.toJson(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?json:"No Employee Found");
        });
        // What is this supposed to be doing differently
        app.put("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            String body = context.body();
            Employee e = gson.fromJson(body, Employee.class);
            e = rs.updateEmployee(e);
            context.status((e!=null)?201:404);
            context.result((e!=null)?"Employee Updated":"No Employee Found");
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
                context.result("No Employee Found");
            }
        });

        app.start(5000);
    }
}
