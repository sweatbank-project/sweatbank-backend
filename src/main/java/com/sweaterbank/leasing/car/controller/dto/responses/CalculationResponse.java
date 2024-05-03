package com.sweaterbank.leasing.car.controller.dto.responses;

import java.math.BigDecimal;

public record CalculationResponse(
        BigDecimal loanServiceRate,
        BigDecimal totalMonthlyPayments
) {
}
