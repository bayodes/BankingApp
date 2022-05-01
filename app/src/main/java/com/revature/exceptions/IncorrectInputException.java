package com.revature.exceptions;

public class IncorrectInputException extends Exception {

    public IncorrectInputException() {
        super("The input entered is invalid");
    }

    public IncorrectInputException(String message) {
        super(message);
    }
}
