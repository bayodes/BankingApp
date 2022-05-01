package com.revature.models;

public class LoginObject {

    public String email;
    public String password;

    @Override
    public String toString() {
        return "LoginObject {" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
