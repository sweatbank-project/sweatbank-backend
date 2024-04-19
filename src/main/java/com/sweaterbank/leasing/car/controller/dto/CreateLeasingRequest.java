package com.sweaterbank.leasing.car.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateLeasingRequest(
        String makes,
        @NotBlank(message = "Field is required")
        String models,

        @NotBlank(message = "Field is required")
        String yearOfManufacture,

        @Min(value = 1, message = "Value must be greater than or equal to 1")
        BigDecimal costOfTheVehicle,

        @NotBlank(message = "Field is required")
        String leasingPeriod,

        @Min(value = 1, message = "Value must be greater than or equal to 1")
        BigDecimal downPayment,

        @NotBlank(message = "Field is required")
        String sellerName,

        @NotBlank(message = "Field is required")
        String education,

        @NotBlank(message = "Field is required")
        String positionHeld,

        @NotBlank(message = "Field is required")
        String jobTitle,

        @NotBlank(message = "Field is required")
        String timeEmployed,

        @NotBlank(message = "Field is required")
        String businessAreaOfYourEmployer,

        @NotBlank(message = "Field is required")
        String maritalStatus,

        @Min(value = 0, message = "Value must be greater than or equal to 0")
        Integer numberOfChildren,

        @Min(value = 1, message = "Value must be greater than or equal to 1")
        BigDecimal monthlyIncomeAfterTaxes,

        @NotBlank(message = "Field is required")
        String obligations,

        BigDecimal customerLoansOutstanding,
        BigDecimal customerLoansMonthlyPayment,
        BigDecimal carLeaseOutstanding,
        BigDecimal carLeaseMonthlyPayment,
        BigDecimal creditCardOutstanding,
        BigDecimal creditCardMonthlyPayment,
        BigDecimal mortgageOutstanding,
        BigDecimal mortgageMonthlyPayment,
        BigDecimal otherCreditsOutstanding,
        BigDecimal otherCreditsMonthlyPayment

) {
}
