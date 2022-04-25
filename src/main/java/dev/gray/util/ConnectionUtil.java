package dev.gray.util;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * A utility class designed to connect to the database
 * and handle resulting errors from trying to connect
 */

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection(){
        Logger log = Logger.getLogger("EmployeeDAOPostgres");
        try {
            return DriverManager.getConnection(System.getenv("REIMDB"));
        } catch (SQLException e) {
            String message = "Unable to connect to database";
            log.error(message);
            return null;
        }

    }
}