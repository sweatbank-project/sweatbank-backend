package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;

public enum IncomeDeductionPercentage {
    SINGLE_ZERO_CHILD(BigDecimal.valueOf(12.7)),
    SINGLE_WITH_CHILD(BigDecimal.valueOf(10)),
    MARRIED_ZERO_CHILD(BigDecimal.valueOf(7.5)),
    MARRIED_WITH_CHILD(BigDecimal.valueOf(6.3)),
    DEFAULT(BigDecimal.ZERO);

    private final BigDecimal percentage;

    IncomeDeductionPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public static IncomeDeductionPercentage fromMaritalStatusAndChildren(String maritalStatus, int numberOfChildren) {
        if ("single".equalsIgnoreCase(maritalStatus) || "divorced".equalsIgnoreCase(maritalStatus) ||
                "widow".equalsIgnoreCase(maritalStatus) || "widower".equalsIgnoreCase(maritalStatus)) {
            return numberOfChildren == 0 ? SINGLE_ZERO_CHILD : SINGLE_WITH_CHILD;
        } else if ("married".equalsIgnoreCase(maritalStatus) || "common law marriage".equalsIgnoreCase(maritalStatus)) {
            return numberOfChildren == 0 ? MARRIED_ZERO_CHILD : MARRIED_WITH_CHILD;
        } else {
            return DEFAULT;
        }
    }
}
