package com.sweaterbank.leasing.car.model.enums;

public enum ObligationType {
    CUSTOMER("customer"),
    CAR_LEASE("carLease"),
    CREDIT_CARD("creditCard"),
    MORTGAGE("mortgage"),
    OTHER_CREDITS("otherCredits");

    private final String obligationType;

    ObligationType (String obligationType) { this.obligationType = obligationType; }

    @Override
    public String toString() { return obligationType; }

    public static ObligationType fromString(String text) throws IllegalArgumentException {
        for (ObligationType b : ObligationType.values()) {
            if (b.obligationType.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
