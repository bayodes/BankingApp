package com.revature.services;

import com.revature.dao.IAccountDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;
import java.util.List;

public class AccountService {

    private IAccountDao aDao;

    public AccountService(){}

    public AccountService(IAccountDao aDao) {
        this.aDao = aDao;
    }

    public User addAccount(User u) {
        Account createAccount = new Account(0, 0.00, u.getUserID());

        u.getListOfAccounts().push(createAccount);

        LoggingUtil.logger.info("Waiting to see if your account is approved");

        return u;
    }

    public void deposit(Account a) {

        aDao.addToAccount(a);
        LoggingUtil.logger.info("Money was just added your account");
    }

    public void withdraw(Account a) {

        aDao.subtractFromAccount(a);
        LoggingUtil.logger.info("Money was just taken out of your account");
    }

    public void transferMoney(int amount, int balance) {

    }

    public List<Account> viewAllAccounts(User u) {

        List<Account> listOfAccounts = aDao.readAllAccounts(u);

        return listOfAccounts;
    }

    public void approveAccount(User u) {
        aDao.createAccount(u.getListOfAccounts().pop(), u);

        LoggingUtil.logger.info("A new account was created");
    }

    public void denyAccount() {

    }

    public void closeAccount() {

    }
}
