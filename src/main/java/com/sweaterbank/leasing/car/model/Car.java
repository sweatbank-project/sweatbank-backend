package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Car(
        String id,
        String make,
        String model,
        int year,
        BigDecimal cost
) {}
