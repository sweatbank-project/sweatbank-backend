package com.sweaterbank.leasing.car.exceptions;

public class MailDataNotFoundException extends Exception{
    public MailDataNotFoundException(String applicationId){
        super("Not able to receive data for the mail from application with ID: %s".formatted(applicationId));
    }
}
