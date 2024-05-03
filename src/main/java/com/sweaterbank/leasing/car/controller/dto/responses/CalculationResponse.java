package com.sweaterbank.leasing.car.controller.dto.responses;

import java.math.BigDecimal;

public record CalculationResponse(
        BigDecimal netIncome,
        BigDecimal currentMonthlyPayment,
        BigDecimal otherMonthlyPayments,
        BigDecimal newMonthlyPayment,
        BigDecimal householdExpenditure,
        BigDecimal monthlyLivingExpenses,
        BigDecimal loanServiceRate
) {
}
