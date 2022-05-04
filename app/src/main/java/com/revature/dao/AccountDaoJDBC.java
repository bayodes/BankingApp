package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;
import java.sql.*;
import java.util.Stack;

public class AccountDaoJDBC implements IAccountDao{

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    @Override
    public void createAccount(Account a, User u) {
        Connection c = cs.getConnection();

        String sql = "insert into accounts (balance, user_fk) values " +
                "('" + a.getBalance() + "','" + u.getUserID() + "')";

        try {
            Statement s = c.createStatement();
            s.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account addToAccount(Account a, double amount) {
        Connection c = cs.getConnection();

        try {
            String sql = "update accounts set balance = ? where user_fk = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, a.getAccountID());

            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return a;
    }

    @Override
    public Account subtractFromAccount(Account a, double amount) {
        Connection c = cs.getConnection();

        try {
            String sql = "update accounts set balance = ? where account_id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, a.getAccountID());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return a;
    }

    @Override
    public Stack<Account> readAllAccounts(User u) {
        Connection c = cs.getConnection();
        PreparedStatement ps;
        ResultSet rs;

        String sql = "select * from accounts";

        try {
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();

            ps.execute();

            Account a;

            while(rs.next()){
                a = new Account(rs.getInt(1), rs.getDouble(2));
                u.getStackOfAccounts().add(a);
                System.out.println(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return u.getStackOfAccounts();
    }

    @Override
    public void closeAccount(Account u) {

    }

    @Override
    public boolean isEmpty(int accountID) {
        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;

        try {
            sql = "select count(*) from users";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") == 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isFound(Account a) {
        Connection c = cs.getConnection();
        String sql;
        PreparedStatement ps;
        ResultSet rs;
        boolean isFound = true;

        try {
            sql = "select count(user_id) from users where user_id = ?";
            ps = c.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());

            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    isFound = true;
                } else {
                    isFound = false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isFound;
    }
}
