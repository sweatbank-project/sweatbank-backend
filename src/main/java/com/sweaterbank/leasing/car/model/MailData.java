package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;

public record MailData(
        String firstName,
        String lastName,
        BigDecimal carCost,
        BigDecimal downPayment,
        BigDecimal margin,
        String euriborType,
        int leasingPeriod
) {
}
