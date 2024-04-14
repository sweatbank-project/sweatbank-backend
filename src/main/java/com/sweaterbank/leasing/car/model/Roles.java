package com.sweaterbank.leasing.car.model;

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
}
