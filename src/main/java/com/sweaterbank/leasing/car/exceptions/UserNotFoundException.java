package com.sweaterbank.leasing.car.exceptions;

public class UserNotFoundException extends UserException{
    public UserNotFoundException() {
        super("No user found with the email");
    }
}
