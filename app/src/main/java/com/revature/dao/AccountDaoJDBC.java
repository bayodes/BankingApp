package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.ConnectionSingleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AccountDaoJDBC implements IAccountDao{

    private ConnectionSingleton cs = ConnectionSingleton.getConnectionSingleton();

    /**
     * @param a
     */
    @Override
    public void createAccount(Account a) {
        Connection c = cs.getConnection();

        String sql = "insert into accounts (balance, users_fk) values " +
                "('" + a.getBalance() + "','" + a.getUserAccount().getUserID() + "')";

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
    public void addToAccount() {

    }

    /**
     *
     */
    @Override
    public void subtractFromAccount() {

    }

    /**
     * @return
     */
    @Override
    public List<Account> readAllAccounts() {
        return null;
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
