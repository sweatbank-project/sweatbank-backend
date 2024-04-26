package com.sweaterbank.leasing.car.controller.dto;

import com.sweaterbank.leasing.car.model.Leasing;

import java.math.BigDecimal;

public record CalculationResponse(
        BigDecimal loanServiceRate,
        BigDecimal totalMonthlyPayments
) {
}
