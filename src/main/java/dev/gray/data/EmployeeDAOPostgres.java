package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/22/2022
 * Implementation
 */
import dev.gray.entities.Employee;
import dev.gray.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO{

    static Logger log = Logger.getLogger("EmployeeDAOPostgres");

    @Override
    public Employee createNew(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into employee values(default,?,?)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, e.getFName());
            ps.setString(2, e.getLName());

            ps.execute();

            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()) {
                int userID = resultSet.getInt("id");
                e.setId(userID);

                String message = "Creating Employee... " + e.getId();
                log.info(message);

                return e;
            }
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public Employee getByID(int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from employee where id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            String message = "Fetching Employee... " + id;
            log.info(message);

            if(rs.next()){
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setFName(rs.getString("first_name"));
                e.setLName(rs.getString("last_name"));
                return e;
            }
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from employee";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = ps.executeQuery();

            String message = "Fetching All Employee... ";
            log.info(message);

            List<Employee> employees = new ArrayList<>();

            while(rs.next()){
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setFName(rs.getString("first_name"));
                e.setLName(rs.getString("last_name"));
                employees.add(e);
            }
            return employees;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        // Something went wrong
        return new ArrayList<>();
    }

    @Override
    public Employee updateEmployee(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update employee set first_name = ?, last_name = ? where id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getFName());
            ps.setString(2, e.getLName());
            ps.setInt(3, e.getId());

            ps.executeUpdate();

            String message = "Updating User... " + e.getId();
            log.info(message);

            return e;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "delete from employee where id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, e.getId());

            ps.execute();

            String message = "Removing User... " + e.getId();
            log.info(message);

            return true;
        }catch (SQLException exc){
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return false;
    }
}
