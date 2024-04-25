package com.sweaterbank.leasing.car.controller.dto;

import com.sweaterbank.leasing.car.model.Leasing;

import java.util.List;

public record GetLeasesResponse(
        List<Leasing> leases
) { }
