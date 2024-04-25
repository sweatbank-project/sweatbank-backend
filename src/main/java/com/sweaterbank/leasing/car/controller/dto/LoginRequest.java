package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull(message = "Username may not be null") String username,
                           @NotNull(message = "Password may not be null") String password) { }
