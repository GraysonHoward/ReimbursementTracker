package dev.gray.util;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * A utility class designed to connect to the database
 * and handle resulting errors from trying to connect
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){

        try {
            return DriverManager.getConnection(System.getenv("REIMDB"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}