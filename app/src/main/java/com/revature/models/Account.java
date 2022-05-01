package com.revature.models;

public class Account {

    private int accountID;
    private double balance;
    private User userAccount;

    public Account() {}

    public Account(int accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }

    public Account(int accountID, double balance, User userAccount) {
        this.accountID = accountID;
        this.balance = balance;
        this.userAccount = userAccount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", balance=" + balance +
                ", userAccount=" + userAccount +
                '}';
    }
}
