package com.revature.models;

import java.util.Stack;

public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Stack stackOfAccounts;
    private String userType;

    public User(){}

//    public User(int userID, String firstName, String lastName, String email, String password) {
//        this.userID = userID;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//        this.stackOfAccounts = new Stack(); // MAKE SURE NOT INIT NEW STACK
//    }

    public User(int userID, String firstName, String lastName, String email, String password, String userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.stackOfAccounts = new Stack();
        this.userType = userType;
    }

    public User(int userID, String firstName, String lastName, String email,
                String password, Stack stackOfAccounts, String userType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.stackOfAccounts = stackOfAccounts;
        this.userType = userType;
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

    public Stack getStackOfAccounts() {
        return stackOfAccounts;
    }

    public void setStackOfAccounts(Stack stackOfAccounts) {

        this.stackOfAccounts = stackOfAccounts;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User {" +
                "userID = " + userID +
                ", firstName ='" + firstName + '\'' +
                ", lastName ='" + lastName + '\'' +
                ", email ='" + email + '\'' +
                ", password ='" + password + '\'' +
                ", stackOfAccounts =" + stackOfAccounts +
                ", userType ='" + userType + '\'' +
                '}';
    }
}