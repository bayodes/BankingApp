package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;
import java.sql.*;
import java.util.Stack;

public class AccountDaoJDBC implements IAccountDao{

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    @Override
    public Account createAccount(Account a, User u) {
        Connection c = cs.getConnection();
        Account ac = null;

        String sql = "insert into accounts (balance, user_fk) values " +
                "('" + a.getBalance() + "','" + u.getUserID() + "')";

        try {
            Statement s = c.createStatement();
            s.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            sql = "select * from accounts where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, a.getUserFK());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ac = new Account(rs.getInt(1), rs.getDouble(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ac;
    }

    @Override
    public void addToAccount(Account a, double newBalance) {
        Connection c = cs.getConnection();

        try {
            String sql = "update accounts set balance = ? where account_id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, newBalance);
            ps.setInt(2, a.getAccountID());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subtractFromAccount(Account a, double newBalance) {
        Connection c = cs.getConnection();

        if (newBalance < 0 ) {
            // throw new exception
        } else {
            try {
                String sql = "update accounts set balance = ? where account_id = ?";

                PreparedStatement ps = c.prepareStatement(sql);
                ps.setDouble(1, newBalance);
                ps.setInt(2, a.getAccountID());

                ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public double[] transferToAccount(int accountOne, int accountTwo, double amount) {
        Connection c = cs.getConnection();
        Account aOne = null;
        Account aTwo = null;
        double startingBalance1 = 0.00;
        double startingBalance2 = 0.00;
        double newBalance1 = 0.00;
        double newBalance2 = 0.00;
        double[] balance = new double[50];

        try {
            String sql = "select * from accounts where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, accountOne);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                aOne = new Account(rs.getInt(1), rs.getDouble(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            String sql = "select * from accounts where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, accountTwo);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                aTwo = new Account(rs.getInt(1), rs.getDouble(2), rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        startingBalance1 = aOne.getBalance();
        balance[0] = startingBalance1;
        startingBalance2 = aTwo.getBalance();
        balance[1] = startingBalance2;
        newBalance1 = startingBalance1 - amount;
        balance[2] = newBalance1;
        newBalance2 = startingBalance2 + amount;
        balance[3] = newBalance2;

        try {
            String sql = "update accounts set balance = ? where account_id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, newBalance1);
            ps.setInt(2, accountOne);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql = "update accounts set balance = ? where account_id = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, newBalance2);
            ps.setInt(2, accountTwo);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }

//    public void inputTransfer(double[] balances, double amount) {
//        Connection c = cs.getConnection();
//
//        try {
//            String sql = "insert into transactions (amount, accounts_fk) values " +
//                    "('" + amount + "','" + u.getLastName() + "')";
//
//            Statement s = c.createStatement();
//            s.execute(sql);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
    public void closeAccount(Account a) {
        Connection c = cs.getConnection();

        try {
            String sql = "delete from accounts where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmpty() {
        Connection c = cs.getConnection();

        try {
            String sql = "select count(*) from accounts";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

        try {
            String sql = "select count(account_id) from accounts where account_id = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, a.getAccountID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("count") == 0) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}