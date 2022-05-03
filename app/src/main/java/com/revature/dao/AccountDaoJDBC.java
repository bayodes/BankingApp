package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;

import java.sql.*;
import java.util.List;

public class AccountDaoJDBC implements IAccountDao{

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    /**
     * @param a
     */
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

    /**
     *
     */
    @Override
    public void addToAccount(Account a) {
        Connection c = cs.getConnection();
        //double amount = a.getBalance();

        String sql = "update accounts set balance = ? where users_fk = ?";

        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, a.getBalance());
            ps.setInt(2, a.getAccountID());

            ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    @Override
    public void subtractFromAccount(Account a) {
        Connection c = cs.getConnection();

        String sql = "update accounts set balance = ? where account_id = ?";

        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setDouble(1, a.getBalance());
            ps.setInt(2, a.getAccountID());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    @Override
    public List<Account> readAllAccounts(User u) {
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
                u.getListOfAccounts().add(a);
                System.out.println(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return u.getListOfAccounts();
    }

    /**
     * @param u
     * @return
     */
    @Override
    public User approveAccount(Account u) {

        return null;
    }

    /**
     * @param u
     * @return
     */
    @Override
    public User denyAccount(Account u) {
        return null;
    }

    /**
     * @param u
     */
    @Override
    public void closeAccount(Account u) {

    }
}
