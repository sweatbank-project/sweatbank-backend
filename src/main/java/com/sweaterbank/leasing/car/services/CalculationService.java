package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.ApplicationIdRequest;
import com.sweaterbank.leasing.car.controller.dto.CalculationResponse;
import com.sweaterbank.leasing.car.model.IncomeDeductionPercentage;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculationService {

    public CalculationResponse calculateLoanServiceRate(LeaseDataForCalculations leaseDataForCalculations) {

        BigDecimal netIncome = leaseDataForCalculations.monthlyIncome();
        BigDecimal monthlyPayment = leaseDataForCalculations.monthlyPayment();
        BigDecimal allObligationPayments = leaseDataForCalculations.allObligationPayments();

        BigDecimal householdExpenditure = calculateHouseholdExpenditure(netIncome, leaseDataForCalculations.maritalStatus().toString(), leaseDataForCalculations.numberOfChildren());


        BigDecimal totalMonthlyPayments = monthlyPayment.add(householdExpenditure);

        if(allObligationPayments != null) {
            totalMonthlyPayments = totalMonthlyPayments.add(allObligationPayments);
        }

        BigDecimal loanServiceRate = totalMonthlyPayments.divide(netIncome, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return new CalculationResponse(loanServiceRate, totalMonthlyPayments);
    }

    private BigDecimal calculateHouseholdExpenditure(BigDecimal netIncome, String maritalStatus, int numberOfChildren) {
        IncomeDeductionPercentage deductionPercentage = IncomeDeductionPercentage.fromMaritalStatusAndChildren(maritalStatus, numberOfChildren);
        BigDecimal percentage = deductionPercentage.getPercentage();

        return netIncome.multiply(percentage.divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
