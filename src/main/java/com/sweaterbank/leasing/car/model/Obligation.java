package com.sweaterbank.leasing.car.model;

import com.sweaterbank.leasing.car.model.enums.ObligationType;

import java.math.BigDecimal;

public record Obligation (
        String id,
        String leasingId,
        ObligationType typeOfObligation,
        BigDecimal outstandingDebt,
        BigDecimal monthlyPayment
) { }
