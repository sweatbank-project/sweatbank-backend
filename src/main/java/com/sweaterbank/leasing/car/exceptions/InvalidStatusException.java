package com.sweaterbank.leasing.car.exceptions;

public class InvalidStatusException extends Exception {
    public InvalidStatusException() {
        super("Invalid status string");
    }
}
