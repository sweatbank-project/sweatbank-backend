package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Date;

public record RegisterRequest(
        @NotEmpty
        @NotBlank
        @NotNull(message = "Username may not be null")
        @Email
        String username,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Field phone number may not be null")
        String phoneNumber,
        @Size(min = 9, max = 9)
        @NotEmpty
        @NotBlank
        @NotNull(message = "Personal ID may not be null")
        String personalId,
        @NotEmpty
        @NotBlank
        @Size(min = 6, message = "The password must be at least 6 characters long")
        @NotNull(message = "Password may not be null")
        String password,
        @NotEmpty
        @NotBlank
        @NotNull(message = "First name may not be null") String firstName,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Last name may not be null") String lastName,
        @NotEmpty
        @NotBlank
        @PastOrPresent
        @NotNull(message = "Birthdate may not be null")
        Date birthDate,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Address may not be null")
        String address
) { }
