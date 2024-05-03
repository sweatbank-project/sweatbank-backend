package com.sweaterbank.leasing.car.model.enums;

public enum EducationType {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    VOCATIONAL("vocational"),
    COLLEGE("college"),
    HIGHER("higher");

    private final String educationType;

    EducationType (String educationType) { this.educationType = educationType; }

    @Override
    public String toString() { return educationType; }
    public static EducationType fromString(String text) throws IllegalArgumentException {
        for (EducationType b : EducationType.values()) {
            if (b.educationType.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
