package com.sweaterbank.leasing.car.model.enums;

public enum AutomationStatus {
    AUTOMATED("automated"),
    MANUAL("manual");

    private final String automationStatus;

    AutomationStatus(String automationStatus) { this.automationStatus = automationStatus; }

    @Override
    public String toString() { return automationStatus; }

    public static AutomationStatus fromString(String text) throws IllegalArgumentException {
        for (AutomationStatus b : AutomationStatus.values()) {
            if (b.automationStatus.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
