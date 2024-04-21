package com.sweaterbank.leasing.car.model;

public enum LeasingStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String leasingStatus;

    LeasingStatus (String status) { this.leasingStatus = status; }

    @Override
    public String toString() { return leasingStatus; }
}
