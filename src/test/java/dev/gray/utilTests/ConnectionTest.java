package dev.gray.utilTests;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Tests to verify connection to the database
 */

import dev.gray.util.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {

    @Test
    void can_connect(){
        Connection conn = ConnectionUtil.createConnection();
        Assertions.assertNotNull(conn);
    }
}