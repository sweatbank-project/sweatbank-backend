package com.sweaterbank.leasing.car.model.enums;

public enum EuriborType {
    EURIBOR_3_MONTH("Euribor 3-month"),
    EURIBOR_6_MONTH("Euribor 6-month"),
    EURIBOR_12_MONTH("Euribor 12-month");

    private final String euriborType;

    EuriborType(String euriborType) {
        this.euriborType = euriborType;
    }
    @Override
    public String toString() { return euriborType; }


    public static EuriborType fromString(String text) throws IllegalArgumentException {
        for (EuriborType b : EuriborType.values()) {
            if (b.euriborType.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }


}
