package com.sweaterbank.leasing.car.controller.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateLeaseRequest(

        @NotBlank(message = "Make field is required")
        String makes,
        @NotBlank(message = "Model field is required")
        String models,

        @NotBlank(message = "Manufacture year field is required")
        String yearOfManufacture,

        @Min(value = 1, message = "Cost of vehicle must be greater than or equal to 1")
        BigDecimal costOfTheVehicle,

        @NotBlank(message = "Leasing period field is required")
        String leasingPeriod,

        @Min(value = 1, message = "Down payment must be greater than or equal to 1")
        BigDecimal downPayment,

        @NotBlank(message = "Seller name field is required")
        String sellerName,

        @NotBlank(message = "Education field is required")
        String education,

        @NotBlank(message = "Position held field is required")
        String positionHeld,

        @NotBlank(message = "Job title field is required")
        String jobTitle,

        @NotBlank(message = "Time employed field is required")
        String timeEmployed,

        @NotBlank(message = "Business area field is required")
        String businessAreaOfYourEmployer,

        @NotBlank(message = "Marital status field is required")
        String maritalStatus,

        @Min(value = 0, message = "Number of children must be greater than or equal to 0")
        Integer numberOfChildren,

        @Min(value = 1, message = "Monthly income must be greater than or equal to 1")
        BigDecimal monthlyIncomeAfterTaxes,

        @NotBlank(message = "Obligations field is required")
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

        @Min(value = 1, message = "Down payment percentage must be greater than or equal to 1")
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
