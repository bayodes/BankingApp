package com.revature.services;

import com.revature.dao.IAccountDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;
import java.util.List;
import java.util.Stack;

public class AccountService {

    private IAccountDao aDao;

    public AccountService(){}

    public AccountService(IAccountDao aDao) {
        this.aDao = aDao;
    }

    public User addAccount(User u) {
        Account createAccount = new Account(0, 0.00, u.getUserID());

        u.getStackOfAccounts().push(createAccount);

        LoggingUtil.logger.info("Waiting to see if your account is approved");

        return u;
    }

    public Account deposit(Account a, double amount) {
        Account account = null;

        account = aDao.addToAccount(a, amount);
        //account.setBalance(amount);
        LoggingUtil.logger.info("Money was just added your account");

        return account;
    }

    public Account withdraw(Account a, double amount) {
        Account account = null;

        account = aDao.subtractFromAccount(a, amount);
        //account.setBalance(amount);
        LoggingUtil.logger.info("Money was just taken out of your account");

        return account;
    }

    public void transferMoney(int amount, int balance) {

    }

    public List<Account> viewAllAccounts(User u) {

        return null;
    }

    public void approveAccount(User u) {
        Account a = (Account) u.getStackOfAccounts().pop();

        u.getStackOfAccounts().push(a);

        aDao.createAccount(a, u);

        LoggingUtil.logger.info("A new account was created");
    }

    public boolean closeAccount(Account a) {
        boolean isEmpty = aDao.isEmpty(a.getAccountID());
        boolean isFound = aDao.isFound(a);

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no users in the database. Can't delete a user that doesn't exist");
            return false;
        } else if (isFound == false) {
            LoggingUtil.logger.error("User with id " + a.getAccountID() + " does not exist in the database");
            return false;
        } else {
            aDao.closeAccount(a);
            LoggingUtil.logger.info("User with id " + a.getAccountID() + " has been deleted from the database");
            return true;
        }
    }
}
