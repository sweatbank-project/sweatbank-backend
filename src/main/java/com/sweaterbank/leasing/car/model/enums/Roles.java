package com.sweaterbank.leasing.car.model.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user");

    private final String role;
    Roles(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return role;
    }

    public static Roles fromString(String text) throws IllegalArgumentException {
        for (Roles b : Roles.values()) {
            if (b.role.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
