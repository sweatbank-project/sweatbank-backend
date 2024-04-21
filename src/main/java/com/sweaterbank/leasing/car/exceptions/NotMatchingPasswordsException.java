package com.sweaterbank.leasing.car.exceptions;

import jakarta.validation.ValidationException;

public class NotMatchingPasswordsException extends ValidationException
{
    public NotMatchingPasswordsException () { super("Password and Confirm Password are not matching"); }
}
