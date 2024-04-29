package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.responses.DashboardResponse;
import com.sweaterbank.leasing.car.controller.dto.requests.MailRequest;
import com.sweaterbank.leasing.car.controller.dto.responses.MailResponse;
import com.sweaterbank.leasing.car.controller.dto.requests.ApplicationIdRequest;
import com.sweaterbank.leasing.car.controller.dto.requests.UpdateLeaseRequest;
import com.sweaterbank.leasing.car.controller.dto.responses.CalculationResponse;
import com.sweaterbank.leasing.car.controller.dto.responses.GetLeasesWithUserDetailsResponse;
import com.sweaterbank.leasing.car.exceptions.InvalidStatusException;
import com.sweaterbank.leasing.car.exceptions.MailDataNotFoundException;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.services.CalculationService;
import com.sweaterbank.leasing.car.services.JwtService;
import com.sweaterbank.leasing.car.services.LeaseService;
import com.sweaterbank.leasing.car.services.MailSenderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final LeaseService leaseService;
    private final MailSenderService mailService;
    private final JwtService jwtService;
    private final CalculationService calculationService;

    public AdminController(LeaseService leasingService, JwtService jwtService, CalculationService calculationService, MailSenderService mailService)
    {
        this.leaseService = leasingService;
        this.mailService = mailService;
        this.jwtService = jwtService;
        this.calculationService = calculationService;
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

    @PostMapping("send-message")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MailResponse> sendMail(@RequestBody MailRequest mailRequest){
        MailResponse mailResponse = mailService.sendEmail(
                mailRequest.applicationId(),
                mailRequest.recipient(),
                mailRequest.subject(),
                mailRequest.body());

        return ResponseEntity.ok(mailResponse);
    }

    @PostMapping("send-message/reject")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MailResponse> sendRejectEmail(@RequestBody MailRequest mailRequest) throws MailDataNotFoundException {
        MailResponse mailResponse = mailService.sendRejectEmail(mailRequest.recipient(), mailRequest.applicationId());

        return ResponseEntity.ok(mailResponse);
    }

    @PostMapping("send-message/approved")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MailResponse> sendApprovedEmail(@RequestBody MailRequest mailRequest) throws MailDataNotFoundException {
        MailResponse mailResponse = mailService.sendApprovedEmail(mailRequest.recipient(), mailRequest.applicationId());

        return ResponseEntity.ok(mailResponse);
    }

    @PostMapping("leases/calculate-solvency")
    public ResponseEntity<CalculationResponse> calculate(@Valid @RequestBody ApplicationIdRequest requestData) throws InvalidStatusException {
        LeaseDataForCalculations leaseDataForCalculations = leaseService.getLeaseById(requestData.applicationId());

        CalculationResponse calculationResponse = calculationService.calculateLoanServiceRate(leaseDataForCalculations);

        return ResponseEntity.ok(calculationResponse);
    }

    @GetMapping("dashboard")
    public ResponseEntity<DashboardResponse> getDashboardData(){
        DashboardResponse dashboardResponse = leaseService.getDashboardData();
        return ResponseEntity.ok(dashboardResponse);
    }

}
