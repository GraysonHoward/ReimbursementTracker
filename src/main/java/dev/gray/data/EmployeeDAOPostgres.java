package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/19/2022
 * Implementation
 */
import dev.gray.entities.Employee;
import dev.gray.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class EmployeeDAOPostgres implements EmployeeDAO{

    static Logger log = Logger.getLogger(EmployeeDAOPostgres.class.getName());

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
                int userID = resultSet.getInt("user_id");
                e.setID(userID);

                String message = "Creating User... " + e.getID();
                log.info(message);

                return e;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error(ex);
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

            String message = "Fetching User... " + id;
            log.info(message);

            if(rs.next()){
                Employee e = new Employee();
                e.setID(rs.getInt("id"));
                e.setFName(rs.getString("first_name"));
                e.setLName(rs.getString("last_name"));
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update employee set first_name = ?, last_name = ? where id = ?)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, e.getFName());
            ps.setString(2, e.getLName());
            ps.setInt(3, e.getID());

            ps.executeUpdate();

            String message = "Updating User... " + e.getID();
            log.info(message);

            return e;
        } catch (SQLException ex) {
            ex.printStackTrace();
            log.error(ex);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "delete * from employee where id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, e.getID());

            ps.execute();

            String message = "Removing User... " + e.getID();
            log.info(message);

            return true;
        }catch (SQLException ex){
            ex.printStackTrace();
            log.error(ex);
        }
        return false;
    }
}
