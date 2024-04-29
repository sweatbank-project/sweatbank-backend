package com.sweaterbank.leasing.car.controller.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateLeaseRequest(
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

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal customerLoansOutstanding,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal customerLoansMonthlyPayment,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal carLeaseOutstanding,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal carLeaseMonthlyPayment,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal creditCardOutstanding,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal creditCardMonthlyPayment,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal mortgageOutstanding,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal mortgageMonthlyPayment,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal otherCreditsOutstanding,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal otherCreditsMonthlyPayment,
        int downPaymentPercentage,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal contractFee,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        String euriborType,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal euriborRate,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal margin,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal interestRate,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BigDecimal monthlyPayment
) {
}
