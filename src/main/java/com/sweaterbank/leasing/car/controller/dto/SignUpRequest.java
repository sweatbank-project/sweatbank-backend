package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.NotNull;

// TODO: add user role
public record SignUpRequest (@NotNull(message = "Username may not be empty") String username, @NotNull(message = "Password may not be empty") String password) { }
