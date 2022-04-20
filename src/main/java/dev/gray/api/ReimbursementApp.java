package dev.gray.api;
/* Author: Grayson Howard
 * Modified: 04/20/2022
 *
 */

import com.google.gson.Gson;
import io.javalin.Javalin;

public class ReimbursementApp {

    static Gson gson = new Gson();

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // Server health check route
        app.get("/", context -> {

        });

        app.post("/employees", context -> {

        });
        app.get("/employees", context -> {

        });
        app.get("/employees/{id}", context -> {

        });
        app.put("/employees/{id}", context -> {

        });
        app.delete("/employees/{id}", context -> {

        });

        app.start(5000);
    }
}
