package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;

public record LeaseDataForCalculations(
        BigDecimal monthlyPayment,
        BigDecimal monthlyIncome,
        int numberOfChildren,
        MaritalStatus maritalStatus,
        BigDecimal allObligationPayments
) {
}
