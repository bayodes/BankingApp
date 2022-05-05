package com.revature.services;

import com.revature.dao.IAccountDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;

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

    public void deposit(Account a, double newBalance) {
        aDao.addToAccount(a, newBalance);

        a.setBalance(newBalance);

        LoggingUtil.logger.info("Money was just added your account");
    }

    public void withdraw(Account a, double newBalance) {

        aDao.subtractFromAccount(a, newBalance);

        a.setBalance(newBalance);

        LoggingUtil.logger.info("Money was just taken out of your account");
    }

    public double[] transferMoney(int accountIdFrom, int accountIdTo, double amount) {
        double[] balances;

        balances = aDao.transferToAccount(accountIdFrom, accountIdTo, amount);
        return balances;
    }

    public Account approveAccount(User u) {
        Account a = (Account) u.getStackOfAccounts().pop();

        Account account = aDao.createAccount(a, u);

        u.getStackOfAccounts().push(account);

        LoggingUtil.logger.info("A new account was created");

        return account;
    }

    public void closeAccount(Account a) {
        boolean isEmpty = aDao.isEmpty();
        boolean isFound = aDao.isFound(a);

        if (isEmpty == true) {
            LoggingUtil.logger.error("There are no accounts in the database. Can't delete an account that doesn't exist");
        } else if (isFound == false) {
            LoggingUtil.logger.error("User with id " + a.getAccountID() + " does not exist in the database");
        } else {
            aDao.closeAccount(a);
            LoggingUtil.logger.info("User with id " + a.getAccountID() + " has been deleted from the database");
        }
    }

//    public void inputTransferTable(double[] balances, double amount) {
//        aDao.inputTransfer(balances, amount);
//    }
}
