package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.*;
import com.sweaterbank.leasing.car.services.LeaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lease")
public class CarLeaseController
{
    private final LeaseService leaseService;
    public CarLeaseController(LeaseService leasingService) {
        this.leaseService = leasingService;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateLeaseRequest requestData) {
        leaseService.createLease(requestData);
    }
}
