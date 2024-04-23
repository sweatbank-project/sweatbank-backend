package com.sweaterbank.leasing.car.model;

public enum ApplicationStatus {

    NEW("new"),
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String applicationStatus;

    ApplicationStatus(String status) { this.applicationStatus = status; }

    @Override
    public String toString() { return applicationStatus; }
}
