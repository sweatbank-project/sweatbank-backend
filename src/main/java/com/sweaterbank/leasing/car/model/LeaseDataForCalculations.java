package com.sweaterbank.leasing.car.model;

import com.sweaterbank.leasing.car.model.enums.MaritalStatus;

import java.math.BigDecimal;

public record LeaseDataForCalculations(
        BigDecimal monthlyPayment,
        BigDecimal monthlyIncome,
        Integer numberOfChildren,
        MaritalStatus maritalStatus,
        BigDecimal allObligationPayments
) {
}
