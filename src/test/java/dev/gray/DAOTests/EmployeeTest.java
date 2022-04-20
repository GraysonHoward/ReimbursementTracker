package dev.gray.DAOTests;
/* Author: Grayson Howard
 * Modified: 04/20/2022
 * CRUD tests for the EmployeeDAO
 */

import dev.gray.data.EmployeeDAO;
import dev.gray.data.EmployeeDAOPostgres;
import dev.gray.entities.Employee;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order
public class EmployeeTest {
    static EmployeeDAO eDAO = new EmployeeDAOPostgres();
    static Employee eTest;

    @Test
    @Order(1)
    void create_employee(){
        eTest = new Employee(0, "Banana", "Man");
        eTest = eDAO.createNew(eTest);
        Assertions.assertNotEquals(0, eTest.getID());
    }

    @Test
    @Order(2)
    void retrieve_employee(){
        Employee retrieved = eDAO.getByID(eTest.getID());
        Assertions.assertEquals(retrieved.getID(), eTest.getID());
    }

    @Test
    @Order(3)
    void retrieve_employee_list(){
        Employee e2 = new Employee(0, "Grape", "Man");
        Employee e3 = new Employee(0, "Orange", "Man");
        Employee e4 = new Employee(0, "Mango", "Man");

        eDAO.createNew(e2);
        eDAO.createNew(e3);
        eDAO.createNew(e4);

        List<Employee> fruitMen = eDAO.getAllEmployee();
        Assertions.assertNotEquals(0, fruitMen.size());

        // Remove testing nonsense from the database
        eDAO.deleteEmployee(e2);
        eDAO.deleteEmployee(e3);
        eDAO.deleteEmployee(e4);
    }

    @Test
    @Order(4)
    void update_employee(){
        eTest.setFName("Apple");
        Employee returned = eDAO.updateEmployee(eTest);
        Assertions.assertEquals("Apple", returned.getFName());
    }

    @Test
    @Order(5)
    void delete_employee(){
        Assertions.assertTrue(eDAO.deleteEmployee(eTest));
    }

}
