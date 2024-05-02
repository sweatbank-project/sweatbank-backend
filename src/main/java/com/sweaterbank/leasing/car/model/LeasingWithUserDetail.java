package com.sweaterbank.leasing.car.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;
import com.sweaterbank.leasing.car.model.enums.AutomationStatus;
import com.sweaterbank.leasing.car.model.enums.BusinessAreaType;
import com.sweaterbank.leasing.car.model.enums.EducationType;
import com.sweaterbank.leasing.car.model.enums.EuriborType;
import com.sweaterbank.leasing.car.model.enums.HeldPositionType;
import com.sweaterbank.leasing.car.model.enums.MaritalStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public record LeasingWithUserDetail(
        String applicationId,
        ApplicationStatus status,
        AutomationStatus automationStatus,
        String carBrand,
        String carModel,
        Integer carManufactureYear,
        BigDecimal carCost,
        BigDecimal downPayment,
        Integer leasingPeriod,
        String carSellerName,
        EducationType education,
        HeldPositionType heldPosition, // CAN BE NULL
        String jobTitle, // CAN BE NULL
        String timeEmployed, // CAN BE NULL
        BusinessAreaType employerBusinessArea, // CAN BE NULL
        MaritalStatus maritalStatus,
        Integer numberOfChildren,
        BigDecimal monthlyIncomeAfterTaxes,
        Integer downPaymentPercentage,
        BigDecimal contractFee,
        EuriborType euriborType,
        BigDecimal euriborRate,
        BigDecimal margin,
        BigDecimal interestRate,
        BigDecimal monthlyPayment,
        List<Obligation> obligations,
        String personalId,
        String fullName,
        String email,
        String phone,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Timestamp creationDate
) {
    public void addObligation(Obligation obligation) {
        this.obligations.add(obligation);
    }
}
