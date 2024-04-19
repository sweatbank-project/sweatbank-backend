package com.sweaterbank.leasing.car.model;

public enum Status {
    PENDING("pending"),
    REJECTED("rejected"),
    APPROVED("approved");

    private final String status;
    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return status;
    }
}
