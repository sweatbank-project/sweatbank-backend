package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Date;

public record SignUpRequest (@NotNull(message = "Username may not be empty") String username,
                             @NotNull(message = "Password may not be empty") String password,
                             @NotNull(message = "First name may not be empty") String firstName,
                             @NotNull(message = "Last name may not be empty") String lastName,
                             @NotNull(message = "Birthdate may not be empty") Date birthDate,
                             @NotNull(message = "Salary may not be empty") BigDecimal salary,
                             @NotNull(message = "Education may not be empty") String education,
                             @NotNull(message = "Position held may not be empty") String positionHeld,
                             @NotNull(message = "Job title may not be empty") String jobTitle) { }
