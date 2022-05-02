package com.revature.services;

import com.revature.dao.IAccountDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utils.LoggingUtil;

import java.util.Arrays;

public class AccountService {

    private IAccountDao aDao;

    public AccountService(){}

    public AccountService(IAccountDao aDao) {
        this.aDao = aDao;
    }

    public void addAccount(User u) {
        Account createAccount = new Account(0, 0.00, u);
        aDao.createAccount(createAccount, u);

        u.getListOfAccounts().add(createAccount);
        LoggingUtil.logger.info("A new account was created");
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

    public Account viewAllAccounts() {
        return null;
    }

    public void approveAccount() {

    }

    public void denyAccount() {

    }

    public void closeAccount() {

    }
}
