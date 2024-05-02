package com.sweaterbank.leasing.car.model.enums;

public enum MaritalStatus {
    SINGLE("single"),
    MARRIED("married"),
    COMMON_LAW_MARRIAGE("commonLawMarriage"),
    DIVORCED("divorced"),
    WINDOW_WIDOWER("windowWindower");

    private final String maritalStatus;

    MaritalStatus (String maritalStatus) { this.maritalStatus = maritalStatus; }

    @Override
    public String toString() { return maritalStatus; }

    public static MaritalStatus fromString(String text) throws IllegalArgumentException {
        for (MaritalStatus ms : MaritalStatus.values()) {
            if (ms.maritalStatus.equalsIgnoreCase(text)) {
                return ms;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
