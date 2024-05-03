package com.sweaterbank.leasing.car.controller.dto.responses;

import com.sweaterbank.leasing.car.model.LeaseDateWithCount;

import java.util.List;

public record DashboardResponse(
        int amountOfNewLeases,
        int amountOfPendingLeases,
        int amountOfApprovedLeases,
        int amountOfRejectedLeases,
        List<LeaseDateWithCount> datesWithCounts
) {
}
