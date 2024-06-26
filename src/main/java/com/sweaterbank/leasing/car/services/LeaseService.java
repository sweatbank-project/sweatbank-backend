package com.sweaterbank.leasing.car.services;

import com.fasterxml.uuid.Generators;
import com.sweaterbank.leasing.car.controller.dto.requests.CreateLeaseRequest;
import com.sweaterbank.leasing.car.controller.dto.responses.DashboardResponse;
import com.sweaterbank.leasing.car.controller.dto.requests.UpdateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.MailDataNotFoundException;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.exceptions.UpdateStatusException;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.LeaseDateWithCount;
import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;
import com.sweaterbank.leasing.car.model.enums.AutomationStatus;
import com.sweaterbank.leasing.car.model.enums.HeldPositionType;
import com.sweaterbank.leasing.car.repository.LeaseRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LeaseService
{

    private final LeaseRepository leaseRepository;
    private final UserService userService;
    private final CalculationService calculationService;
    private final MailSenderService mailService;
    private final BigDecimal APPROVE_MAX_CAR_COST = new BigDecimal(55000);
    private final BigDecimal APPROVE_DOWN_PAYMENT_LOWER_BOUND = new BigDecimal(10);
    private final BigDecimal APPROVE_DOWN_PAYMENT_UPPER_BOUND = new BigDecimal(60);
    private final BigDecimal MAX_LOAN_SERVICE_RATE = new BigDecimal(40);

    public LeaseService(LeaseRepository leaseRepository, UserService userService, CalculationService calculationService, MailSenderService mailService)
    {
        this.leaseRepository = leaseRepository;
        this.userService = userService;
        this.calculationService = calculationService;
        this.mailService = mailService;
    }

    public void createLease(CreateLeaseRequest requestData, String email) throws PendingLeasesException, MailDataNotFoundException {
        String userId = userService.getUserIdByUsername(email);
        if(leaseRepository.getAmountOfPendingLeasesByUserId(userId) == 0 && leaseRepository.getAmountOfNewLeasesByUserId(userId) == 0){
            String leaseId = Generators.timeBasedEpochGenerator().generate().toString();

            ApplicationStatus applicationStatus = automatedDecision(requestData);
            AutomationStatus automationStatus = applicationStatus == ApplicationStatus.NEW ? AutomationStatus.MANUAL
                    : AutomationStatus.AUTOMATED;

            String applicationId = leaseRepository.createLease(requestData, leaseId, applicationStatus, automationStatus);
            leaseRepository.saveUserIdWithLeaseId(userId, leaseId);

            if(applicationStatus.toString().equals("approved")){
                mailService.sendApprovedEmail(email, applicationId);
            }

            if(applicationStatus.toString().equals("rejected")){
                mailService.sendRejectEmail(email, applicationId);
            }

        } else {
            throw new PendingLeasesException();
        }
    }

    public ApplicationStatus automatedDecision(CreateLeaseRequest request)
    {
        HeldPositionType heldPosition = HeldPositionType.fromString(request.positionHeld().toUpperCase());
        if (heldPosition == HeldPositionType.STUDENT ||
                heldPosition == HeldPositionType.UNEMPLOYED) {
            return ApplicationStatus.REJECTED;
        }

        BigDecimal loanServiceRate = calculationService.getServiceLoanRateForNewLease(request);
        if (loanServiceRate.compareTo(MAX_LOAN_SERVICE_RATE) >= 0) {
            return ApplicationStatus.REJECTED;
        }

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

        int timeEmployed = Integer.parseInt(request.timeEmployed());
        if (timeEmployed <= 1) {
            return ApplicationStatus.NEW;
        }

        return ApplicationStatus.APPROVED;
    }

    public List<LeasingWithUserDetail> getLeasesWithUserDetails() throws DataAccessException {
        return leaseRepository.getAllLeasesWithUserDetails();
    }

    public void updateLease(UpdateLeaseRequest requestData) throws UpdateStatusException {
        String status = leaseRepository.getApplicationStatusById(requestData.applicationId());

        if(status == null || status.equalsIgnoreCase("approved") || (status.equalsIgnoreCase("rejected"))) {
            throw new UpdateStatusException();
        }

        leaseRepository.updateLease(requestData);
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

    public DashboardResponse getDashboardData(){
        int countOfNewLeases = leaseRepository.countingAllLeasesByStatus("new");
        int countOfPendingLeases = leaseRepository.countingAllLeasesByStatus("pending");
        int countOfApprovedLeases = leaseRepository.countingAllLeasesByStatus("approved");
        int countOfRejectedLeases = leaseRepository.countingAllLeasesByStatus("rejected");
        List<LeaseDateWithCount> datesWithCounts = leaseRepository.getLeaseDatesWithCount();

        return new DashboardResponse(countOfNewLeases, countOfPendingLeases, countOfApprovedLeases, countOfRejectedLeases, datesWithCounts);

    }
}
