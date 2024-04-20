package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.GetLeasesResponse;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.services.LeaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final LeaseService leaseService;

    public AdminController(LeaseService leasingService)
    {
        this.leaseService = leasingService;
    }

    @GetMapping("leases")
    public ResponseEntity<GetLeasesResponse> getAllLeases() {
        List<Leasing> leases = leaseService.getLeases();

        return ResponseEntity.ok(new GetLeasesResponse(leases));
    }
}
