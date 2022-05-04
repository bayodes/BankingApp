package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import java.util.List;
import java.util.Stack;

public interface IAccountDao {

    public void createAccount(Account a, User u);

    public Account addToAccount(Account a, double amount);

    public Account subtractFromAccount(Account a, double amount);

    //public void transferToAccount();

    public List<Account> readAllAccounts(User u);

    public void closeAccount(Account u);

    public boolean isEmpty(int accountID);

    public boolean isFound(Account a);
}
