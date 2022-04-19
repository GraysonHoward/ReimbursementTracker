package dev.gray.api;

import com.google.gson.Gson;
import io.javalin.Javalin;

public class ReimbursementApp {
    static Gson gson = new Gson();
    public static void main(String[] args) {
        Javalin app = Javalin.create();

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

        app.start(7000);
    }
}
