package com.revature.models;

public class RegisterObject {

    public String firstName;
    public String lastName;
    public String email;
    public String password;

    @Override
    public String toString() {
        return "RegisterObject {" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
