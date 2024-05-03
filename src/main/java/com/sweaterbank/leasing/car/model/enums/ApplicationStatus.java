package com.sweaterbank.leasing.car.model.enums;

public enum ApplicationStatus {

    NEW("new"),
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String applicationStatus;

    ApplicationStatus(String status) { this.applicationStatus = status; }

    @Override
    public String toString() { return applicationStatus; }

    public static ApplicationStatus fromString(String text) throws IllegalArgumentException {
        for (ApplicationStatus b : ApplicationStatus.values()) {
            if (b.applicationStatus.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
