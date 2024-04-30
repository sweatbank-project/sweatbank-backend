package com.sweaterbank.leasing.car.controller.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateLeaseRequest(

        @NotBlank(message = "applicationId field is required")
        String applicationId,

        @NotBlank(message = "status field is required")
        String status,
        @NotNull(message = "downPayment field is required")
        BigDecimal downPayment,
        @NotNull(message = "downPaymentPercentage field is required")
        BigDecimal downPaymentPercentage,
        @NotNull(message = "euriborRate field is required")
        BigDecimal euriborRate,
        @NotBlank(message = "euriborType field is required")
        String euriborType,
        @NotNull(message = "interestRate field is required")
        BigDecimal interestRate,
        @NotNull(message = "leasingPeriod status field is required")
        int leasingPeriod,
        @NotNull(message = "margin status field is required")
        BigDecimal margin,
        @NotNull(message = "monthlyPayment status field is required")
        BigDecimal monthlyPayment
) { }
