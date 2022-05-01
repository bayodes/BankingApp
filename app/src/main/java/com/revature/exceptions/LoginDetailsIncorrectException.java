package com.revature.exceptions;

public class LoginDetailsIncorrectException extends Exception {

    public LoginDetailsIncorrectException() {
        super("User entered the wrong email or password");
    }

    public LoginDetailsIncorrectException(String message) {
        super(message);
    }
}
