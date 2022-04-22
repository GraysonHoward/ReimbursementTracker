package dev.gray.data;
/* Author: Grayson Howard
 * Modified: 04/22/2022
 * CRUD implementation for ExpenseDAO
 */

import dev.gray.entities.Expense;
import dev.gray.entities.Status;
import dev.gray.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {

    static Logger log = Logger.getLogger("ExpenseDAOPostgres");

    @Override
    public Expense createExpense(Expense ex) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into expense values(default,?,?,default)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ex.getEmplId());
            ps.setDouble(2, ex.getAmount());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                ex.setExpId(rs.getInt("exp_id"));
                ex.setStat(Status.valueOf(rs.getString("stat")));
                String message = String.format("Creating Expenditure... %d for $%.2f", ex.getExpId(), ex.getAmount());
                log.info(message);
                return ex;
            }
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public Expense getExpenseByID(int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where exp_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            String message = "Fetching Expenditure... " + id;
            log.info(message);

            if(rs.next()){
                Expense ex = new Expense();
                ex.setExpId(rs.getInt("exp_id"));
                ex.setEmplId(rs.getInt("employee"));
                ex.setAmount(rs.getDouble("amount"));
                // Keep an eye on this
                ex.setStat(Status.valueOf(rs.getString("stat")));

                return ex;
            }
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public List<Expense> expenses(int id) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where employee = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            String message = String.format("Fetching Expenditures for: %d", id);
            log.info(message);

            List<Expense> expenses = new ArrayList<>();
            while(rs.next()){
                Expense ex = new Expense();
                ex.setExpId(rs.getInt("exp_id"));
                ex.setEmplId(rs.getInt("employee"));
                ex.setAmount(rs.getDouble("amount"));
                ex.setStat(Status.valueOf(rs.getString("stat")));
                expenses.add(ex);
            }
            return expenses;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        // Something went wrong
        return new ArrayList<>();
    }

    @Override
    public List<Expense> expenses(Status stat) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where stat = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, stat.getStatus());

            ResultSet rs = ps.executeQuery();

            String message = String.format("Fetching Expenditures With Status: %s", stat);
            log.info(message);

            List<Expense> expenses = new ArrayList<>();
            while(rs.next()){
                Expense ex = new Expense();
                ex.setExpId(rs.getInt("exp_id"));
                ex.setEmplId(rs.getInt("employee"));
                ex.setAmount(rs.getDouble("amount"));
                ex.setStat(Status.valueOf(rs.getString("stat")));
                expenses.add(ex);
            }
            return expenses;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        // Something went wrong
        return new ArrayList<>();
    }

    @Override
    public List<Expense> expenses() {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from expense";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = ps.executeQuery();

            String message = "Fetching All Expenditures...";
            log.info(message);

            List<Expense> expenses = new ArrayList<>();
            while(rs.next()){
                Expense ex = new Expense();
                ex.setExpId(rs.getInt("exp_id"));
                ex.setEmplId(rs.getInt("employee"));
                ex.setAmount(rs.getDouble("amount"));
                ex.setStat(Status.valueOf(rs.getString("stat")));
                expenses.add(ex);
            }
            return expenses;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        // Something went wrong
        return new ArrayList<>();
    }

    @Override
    public Expense updateExpense(Expense ex) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "update expense set employee = ?, amount = ?, stat = ? where exp_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ex.getEmplId());
            ps.setDouble(2, ex.getAmount());
            ps.setString(3, ex.getStat().getStatus());
            ps.setInt(4,ex.getExpId());

            ps.executeUpdate();

            String message = "Updating Expenditure " + ex.getExpId();
            log.info(message);

            return ex;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return null;
    }

    @Override
    public boolean deleteExpense(Expense ex) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "delete from expense where exp_id = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ex.getExpId());

            ps.execute();

            String message = "Removing Expenditure " + ex.getExpId();
            log.info(message);

            return true;
        } catch (SQLException exc) {
            StackTraceElement[] trace = exc.getStackTrace();
            String message = String.format("Method: %s Line: %d | SQL State: %s | Message: %s",trace[0].getMethodName(),trace[0].getLineNumber(),exc.getSQLState(),exc.getMessage());
            log.error(message);
        }
        return false;
    }
}
