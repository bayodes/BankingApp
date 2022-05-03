package com.revature.models;

public class RegisterObject {

    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String userType;

    @Override
    public String toString() {
        return "RegisterObject{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
