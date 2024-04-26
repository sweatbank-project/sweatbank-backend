package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.util.List;

public record Leasing(
        String id,
        String applicationId,
        ApplicationStatus status,
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
        BigDecimal monthlyIncomeAfterTaxes,
        int downPaymentPercentage,
        BigDecimal contractFee,
        EuriborType euriborType,
        BigDecimal euriborRate,
        BigDecimal margin,
        BigDecimal interestRate,
        BigDecimal monthlyPayment,
        List<Obligation> obligations
) {
    public void addObligation(Obligation obligation) {
        this.obligations.add(obligation);
    }
}
