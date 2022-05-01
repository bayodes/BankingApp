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

    public void addAccount(User u) {
        Account createAccount = new Account(0, 0.00, u);
        aDao.createAccount(createAccount);


        System.out.println(u);
        u.getListOfAccounts().add(createAccount);
        LoggingUtil.logger.info("A new account was created");
    }

    public void deposit(int amount) {

    }

    public void withdraw(int amount, int balance) {

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
