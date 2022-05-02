package com.revature.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Account> listOfAccounts;
    private List<User> listOfUsers;

    public User(){}

    public User(int userID, String firstName, String lastName, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.listOfAccounts = new ArrayList<>();
        this.listOfUsers = new ArrayList<>();
    }

    public User(int userID, String firstName, String lastName, String email,
                String password, List<Account> listOfAccounts, List<User> listOfUsers) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.listOfAccounts = listOfAccounts;
        this.listOfUsers = listOfUsers;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getListOfAccounts() {
        return listOfAccounts;
    }

    public void setListOfAccounts(List<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts;
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", listOfAccounts=" + listOfAccounts +
                ", listOfUsers=" + listOfUsers +
                '}';
    }
}