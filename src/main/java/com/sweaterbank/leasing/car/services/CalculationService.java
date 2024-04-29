package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.CalculationResponse;
import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.model.IncomeDeductionPercentage;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.MaritalStatus;
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

        if (allObligationPayments != null) {
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

    public BigDecimal getServiceLoanRateForNewLease(CreateLeaseRequest request) {
        BigDecimal obligationsSum = getObligationsSum(request);
        MaritalStatus maritalStatus = MaritalStatus.fromString(request.maritalStatus().toUpperCase());

        LeaseDataForCalculations leaseDataForCalculations = new LeaseDataForCalculations(request.monthlyPayment(),
                request.monthlyIncomeAfterTaxes(), request.numberOfChildren(), maritalStatus, obligationsSum);

        CalculationResponse calculationResponse = calculateLoanServiceRate(leaseDataForCalculations);

        return calculationResponse.loanServiceRate();
    }

    private BigDecimal getObligationsSum(CreateLeaseRequest request) {
        BigDecimal obligationsSum = new BigDecimal(0);

        if (request.carLeaseMonthlyPayment() != null) {
            obligationsSum = obligationsSum.add(request.carLeaseMonthlyPayment());
        }

        if (request.mortgageMonthlyPayment() != null) {
            obligationsSum = obligationsSum.add(request.mortgageMonthlyPayment());
        }

        if (request.creditCardMonthlyPayment() != null) {
            obligationsSum = obligationsSum.add(request.creditCardMonthlyPayment());
        }

        if (request.customerLoansMonthlyPayment() != null) {
            obligationsSum = obligationsSum.add(request.customerLoansMonthlyPayment());
        }

        if (request.otherCreditsMonthlyPayment() != null) {
            obligationsSum = obligationsSum.add(request.otherCreditsMonthlyPayment());
        }

        return obligationsSum;
    }
}
