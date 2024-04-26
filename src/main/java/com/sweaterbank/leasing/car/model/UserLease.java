package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.util.Date;

public record UserLease (
        String applicationId,
        ApplicationStatus status,
        Date applicationDate,
        String interestBase,
        float leaseMargin,
        BigDecimal monthlyPayment,
        BigDecimal borrowedAmount
) { }
