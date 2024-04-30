package com.sweaterbank.leasing.car.model;

import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;

import java.math.BigDecimal;
import java.util.Date;

public record UserLease (
        String applicationId,
        ApplicationStatus status,
        Date applicationDate,
        String interestBase,
        BigDecimal leaseMargin,
        BigDecimal monthlyPayment,
        BigDecimal borrowedAmount
) { }
