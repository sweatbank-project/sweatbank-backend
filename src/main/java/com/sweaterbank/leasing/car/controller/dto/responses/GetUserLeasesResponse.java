package com.sweaterbank.leasing.car.controller.dto.responses;

import com.sweaterbank.leasing.car.model.UserLease;

import java.util.List;

public record GetUserLeasesResponse (
        List<UserLease> leases
) { }
