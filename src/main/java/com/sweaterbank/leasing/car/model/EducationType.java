package com.sweaterbank.leasing.car.model;

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
}
