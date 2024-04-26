package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.controller.dto.UpdateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.InvalidStatusException;
import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.repository.LeaseRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaseService
{

    private final LeaseRepository leaseRepository;
    private final UserService userService;

    public LeaseService(LeaseRepository leaseRepository, UserService userService)
    {
        this.leaseRepository = leaseRepository;
        this.userService = userService;
    }

    public void createLease(CreateLeaseRequest requestData, String email) throws PendingLeasesException {
        String userId = userService.getUserIdByUsername(email);
        if(leaseRepository.getAmountOfPendingLeases(userId) == 0 && leaseRepository.getAmountOfNewLeases(userId) == 0){
            String leaseId = UUID.randomUUID().toString();
            leaseRepository.createLease(requestData, leaseId);
            leaseRepository.saveUserIdWithLeaseId(userId, leaseId);
        }else{
            throw new PendingLeasesException();
        }
    }

    public List<Leasing> getLeases() throws DataAccessException {
        return leaseRepository.getAllLeases();
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
