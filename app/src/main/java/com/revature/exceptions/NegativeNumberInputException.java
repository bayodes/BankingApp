package com.revature.exceptions;

public class NegativeNumberInputException extends Exception {

    public NegativeNumberInputException() {

        super("You cannot deposit or withdraw a negative number");
    }

    public NegativeNumberInputException(String message) {
        super(message);
    }
}
