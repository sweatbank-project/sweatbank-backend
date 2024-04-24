package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.GetLeasesWithUserDetailsResponse;
import com.sweaterbank.leasing.car.controller.dto.UpdateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.InvalidStatusException;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.services.LeaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public ResponseEntity<GetLeasesWithUserDetailsResponse> getAllLeases() {
        List<LeasingWithUserDetail> leases = leaseService.getLeasesWithUserDetails();

        return ResponseEntity.ok(new GetLeasesWithUserDetailsResponse(leases));
    }

    @PutMapping("leases/update-status/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id, @Valid @RequestBody UpdateLeaseRequest requestData) throws InvalidStatusException {
        leaseService.updateLease(id, requestData);
    }
}
