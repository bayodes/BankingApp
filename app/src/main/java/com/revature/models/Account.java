package com.revature.models;

public class Account {

    private int accountID;
    private double balance;
    private int userFK;

    public Account() {}

    public Account(int accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }

    public Account(int accountID, double balance, int userFK) {
        this.accountID = accountID;
        this.balance = balance;
        this.userFK = userFK;
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

    public int getUserFK() {
        return userFK;
    }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    @Override
    public String toString() {
        return "Account\t{\n" +
                "accountID = " + accountID +
                ",\nbalance = " + balance +
                ",\nuserFK = " + userFK +
                "\n}";
    }
}
