package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import java.util.List;
import java.util.Stack;

public interface IAccountDao {

    public Account createAccount(Account a, User u);

    public void addToAccount(Account a, double amount);

    public void subtractFromAccount(Account a, double amount);

    public double[] transferToAccount(int accountOne, int accountTwo, double amount);

//    public void inputTransfer(double[] balances, double amount);

    public List<Account> readAllAccounts(User u);

    public void closeAccount(Account a);

    public boolean isEmpty();

    public boolean isFound(Account a);
}
