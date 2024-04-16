package com.sweaterbank.leasing.car.exceptions;

public class NotMatchingPasswordsException extends Exception {
    public NotMatchingPasswordsException () { super("Password and Confirm Password are not matching"); }
}
