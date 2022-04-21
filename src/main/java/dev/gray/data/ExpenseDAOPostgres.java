package dev.gray.data;

import dev.gray.entities.Employee;
import dev.gray.entities.Expense;
import dev.gray.entities.Status;
import dev.gray.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgres implements ExpenseDAO {

    static Logger log = Logger.getLogger(EmployeeDAOPostgres.class.getName());

    @Override
    public Expense createExpense(Expense ex) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "insert into expense values(default,?,?,default)";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ex.getEmplId());
            ps.setDouble(2, ex.getAmount());

            ps.execute();

            ResultSet resultSet = ps.getGeneratedKeys();
            if(resultSet.next()) {
                int expId = resultSet.getInt("exp_id");
                ex.setExpId(expId);

                String message = String.format("Creating Expenditure... %d for $%.2f", ex.getExpId(), ex.getAmount());
                log.info(message);

                return ex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
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

            String message = "Fetching Expense... " + id;
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
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public List<Expense> expenses(Employee e) {
        try(Connection conn = ConnectionUtil.createConnection()) {
            String sql = "select * from expense where employee = ?";
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, e.getId());

            ResultSet rs = ps.executeQuery();

            String message = String.format("Fetching Expenditures for: %d", e.getId());
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
            exc.printStackTrace();
            log.error(exc);
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
            exc.printStackTrace();
            log.error(exc);
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

            ps.executeUpdate();

            String message = "Updating Expenditure " + ex.getExpId();
            log.info(message);

            return ex;
        } catch (SQLException exc) {
            exc.printStackTrace();
            log.error(exc);
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
            exc.printStackTrace();
            log.error(exc);
        }
        return false;
    }
}
