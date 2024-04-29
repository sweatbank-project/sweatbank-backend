package com.sweaterbank.leasing.car.controller.dto;

import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;

import java.util.List;

public record GetLeasesWithUserDetailsResponse(
        List<LeasingWithUserDetail> leases
) { }
