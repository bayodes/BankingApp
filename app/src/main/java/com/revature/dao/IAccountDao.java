package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import java.util.List;

public interface IAccountDao {

    public void createAccount(Account a, User u);

    public void addToAccount(Account amount);

    public void subtractFromAccount(Account amount);

    //public void transferToAccount();

    public List<Account> readAllAccounts(User u);

    public User approveAccount(Account u);

    public User denyAccount(Account u);

    public void closeAccount(Account u);
}
