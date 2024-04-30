package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.requests.CreateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.services.LeaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
    public void create(@Valid @RequestBody CreateLeaseRequest requestData, Principal principal) throws PendingLeasesException{
        String email = principal.getName();
        leaseService.createLease(requestData, email);
    }
}
