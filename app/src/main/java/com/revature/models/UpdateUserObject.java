package com.revature.models;

public class UpdateUserObject {
    public String firstName;
    public String lastName;
    public String password;

    @Override
    public String toString() {
        return "UpdateUserObject{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
