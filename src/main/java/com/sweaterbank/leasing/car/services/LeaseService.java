package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.controller.dto.UpdateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.InvalidStatusException;
import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.AutomationStatus;
import com.sweaterbank.leasing.car.model.HeldPositionType;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.repository.LeaseRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaseService
{

    private final LeaseRepository leaseRepository;
    private final UserService userService;
    private final CalculationService calculationService;

    private final BigDecimal APPROVE_MAX_CAR_COST = new BigDecimal(55000);
    private final BigDecimal APPROVE_DOWN_PAYMENT_LOWER_BOUND = new BigDecimal(10);
    private final BigDecimal APPROVE_DOWN_PAYMENT_UPPER_BOUND = new BigDecimal(60);
    private final BigDecimal MAX_LOAN_SERVICE_RATE = new BigDecimal(40);

    public LeaseService(LeaseRepository leaseRepository, UserService userService, CalculationService calculationService)
    {
        this.leaseRepository = leaseRepository;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    public void createLease(CreateLeaseRequest requestData, String email) throws PendingLeasesException, InvalidStatusException {
        String userId = userService.getUserIdByUsername(email);
        if(leaseRepository.getAmountOfPendingLeases(userId) == 0 && leaseRepository.getAmountOfNewLeases(userId) == 0){
            String leaseId = UUID.randomUUID().toString();

            ApplicationStatus applicationStatus = automatedDecision(requestData, leaseId);
            AutomationStatus automationStatus = applicationStatus == ApplicationStatus.NEW ? AutomationStatus.MANUAL
                    : AutomationStatus.AUTOMATED;

            leaseRepository.createLease(requestData, leaseId, applicationStatus, automationStatus);
            leaseRepository.saveUserIdWithLeaseId(userId, leaseId);
        } else {
            throw new PendingLeasesException();
        }
    }

    public ApplicationStatus automatedDecision(CreateLeaseRequest request, String leaseId) throws InvalidStatusException
    {
        if (request.costOfTheVehicle().compareTo(APPROVE_MAX_CAR_COST) > 0) {
            return ApplicationStatus.NEW;
        }

        BigDecimal downPaymentPercentage = new BigDecimal(request.downPaymentPercentage());
        if (downPaymentPercentage.compareTo(APPROVE_DOWN_PAYMENT_LOWER_BOUND) < 0 &&
            downPaymentPercentage.compareTo(APPROVE_DOWN_PAYMENT_UPPER_BOUND) > 0) {
            return ApplicationStatus.NEW;
        }

        int yearOfManufacture = Integer.parseInt(request.yearOfManufacture());
        if (yearOfManufacture < 2020) {
            return ApplicationStatus.NEW;
        }

        HeldPositionType heldPosition = HeldPositionType.fromString(request.positionHeld().toUpperCase());
        if (heldPosition == HeldPositionType.STUDENT ||
            heldPosition == HeldPositionType.UNEMPLOYED) {
            return ApplicationStatus.REJECTED;
        }

        int timeEmployed = Integer.parseInt(request.timeEmployed());
        if (timeEmployed <= 1) {
            return ApplicationStatus.NEW;
        }

        BigDecimal loanServiceRate = calculationService.getServiceLoanRateForNewLease(request);
        if (loanServiceRate.compareTo(MAX_LOAN_SERVICE_RATE) >= 0) {
            return ApplicationStatus.REJECTED;
        }

        return ApplicationStatus.APPROVED;
    }

    public List<LeasingWithUserDetail> getLeasesWithUserDetails() throws DataAccessException {
        return leaseRepository.getAllLeasesWithUserDetails();
    }

    public void updateLease(String leaseId, UpdateLeaseRequest requestData) throws InvalidStatusException {
        try {
            ApplicationStatus status = ApplicationStatus.valueOf(requestData.status().toUpperCase());
            leaseRepository.updateLease(leaseId, status.toString());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException();
        }
    }

    public LeaseDataForCalculations getLeaseById(String id) {
        Optional<LeaseDataForCalculations> optionalLeaseData = leaseRepository.getLeaseCalculationData(id);

        if (optionalLeaseData.isPresent()) {
            return optionalLeaseData.get();
        }
        else {
            throw new NoSuchElementException("Not able to receive data from Application ID: " + id);
        }
    }
}
