package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Leasing(
        String id,
        String userId,
        String carId,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal monthlyPayment,
        BigDecimal deposit,
        String status
) {
}
