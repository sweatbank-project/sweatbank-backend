package com.sweaterbank.leasing.car.exceptions;

public class AccountExistsException extends Exception {
    public AccountExistsException () { super("Email already exists"); }
}
