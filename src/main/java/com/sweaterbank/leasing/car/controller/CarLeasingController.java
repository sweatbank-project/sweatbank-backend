package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.*;
import com.sweaterbank.leasing.car.services.LeasingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/leasing")
public class CarLeasingController {
    private final LeasingService leasingService;
    public CarLeasingController(LeasingService leasingService) {
        this.leasingService = leasingService;
    }

    @PostMapping("create")
    public ResponseEntity<CreateLeasingResponse> create(@Valid @RequestBody CreateLeasingRequest requestData) {
        leasingService.createLeasing(requestData);

        return ResponseEntity.ok(new CreateLeasingResponse("Leasing created"));
    }
}
