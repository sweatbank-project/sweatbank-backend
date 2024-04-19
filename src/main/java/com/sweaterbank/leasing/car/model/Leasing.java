package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public record Leasing(
        String id,
        LeasingStatus status,
        String carBrand,
        String carModel,
        int carManufactureYear,
        BigDecimal carCost,
        int leasingPeriod,
        String carSellerName,
        EducationType education,
        HeldPositionType heldPosition, // CAN BE NULL
        String jobTitle, // CAN BE NULL
        String timeEmployed, // CAN BE NULL
        BusinessAreaType employerBusinessArea, // CAN BE NULL
        MaritalStatus maritalStatus,
        int numberOfChildren,
        BigDecimal monthlyIncomeAfterTaxes
) {
}
