package com.revature.dao;

import com.revature.models.Account;
import com.revature.models.User;
import java.util.List;

public interface IAccountDao {

    public void createAccount(Account a);

    public void addToAccount();

    public void subtractFromAccount();

    //public void transferToAccount();

    public List<Account> readAllAccounts();

    public User approveAccount(Account u);

    public User denyAccount(Account u);

    public void closeAccount(Account u);
}
