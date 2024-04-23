package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLeaseRequest(
        @NotNull
        @NotBlank(message = "Field is required")
        String status
) { }
