package com.sweaterbank.leasing.car.model.enums;

public enum Role {
    ADMIN("admin"),
    USER("user");

    private final String role;
    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return role;
    }

    public static Role fromString(String text) throws IllegalArgumentException {
        for (Role b : Role.values()) {
            if (b.role.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
