package com.sweaterbank.leasing.car.model.enums;

public enum AutomationStatus {
    AUTOMATED("automated"),
    MANUAL("manual");

    private final String automationStatus;

    AutomationStatus(String automationStatus) { this.automationStatus = automationStatus; }

    @Override
    public String toString() { return automationStatus; }
}
