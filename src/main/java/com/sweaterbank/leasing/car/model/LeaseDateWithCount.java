package com.sweaterbank.leasing.car.model;

import java.util.Date;

public record LeaseDateWithCount(
        Date leaseCreationDate,
        int countOfLeases
) {
}
