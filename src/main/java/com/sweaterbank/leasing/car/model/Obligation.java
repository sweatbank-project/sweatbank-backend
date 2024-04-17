package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;

public record Obligation (
        String id,
        String leasingId,
        String typeOfObligation,
        BigDecimal outstandingDebt,
        BigDecimal monthlyPayment
) { }
