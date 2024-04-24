package com.sweaterbank.leasing.car.exceptions;

public class ApplicationIdExistsException extends Exception {
    public ApplicationIdExistsException() { super("Failed to generate Application ID for Lease"); }
}
