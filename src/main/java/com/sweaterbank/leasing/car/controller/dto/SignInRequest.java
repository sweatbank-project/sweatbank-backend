package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(@NotNull(message = "Login may not be null") String login,
                            @NotNull(message = "Password may not be null") String password) { }
