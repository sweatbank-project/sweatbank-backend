package com.sweaterbank.leasing.car.exceptions;

public class PendingLeasesException extends Exception{
    public PendingLeasesException(){
        super("There are already pending leases. They have to be rejected or approved to create a new lease");
    }
}
