package com.sweaterbank.leasing.car.model;

public record User(
        String id,
        String personalDataId,
        String username,
        String password,
        String role
) {
}
