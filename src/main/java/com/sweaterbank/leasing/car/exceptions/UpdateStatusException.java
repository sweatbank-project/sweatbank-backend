package com.sweaterbank.leasing.car.exceptions;

public class UpdateStatusException extends Exception{
    public UpdateStatusException() {
        super("Can't update lease application status.");
    }
}
