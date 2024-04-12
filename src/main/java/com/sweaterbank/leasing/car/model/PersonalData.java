package com.sweaterbank.leasing.car.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonalData(
        String id,
        String name,
        String surname,
        LocalDate birthdate,
        String identityNumber,
        BigDecimal salary,
        String education,
        String positionHeld,
        String jobTitle
) {
}
