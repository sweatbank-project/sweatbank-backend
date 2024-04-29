package com.sweaterbank.leasing.car.controller.dto;

import java.util.Date;

public record UserDto(
        String firstName,
        String lastName,
        String username,
        String phoneNumber,
        String address,
        Date birthdate
) {}
