package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.User;

import java.util.Collection;
import java.util.Optional;

public interface LeaseRepositoryInterface
{
    void createLease(CreateLeaseRequest requestData, String leaseId) throws PendingLeasesException;
    void createObligations(CreateLeaseRequest requestData, String leaseId);
    String generateApplicationId();
    Collection<Leasing> getAllLeases();
    Collection<LeasingWithUserDetail> getAllLeasesWithUserDetails();
    void saveUserIdWithLeaseId(String userId, String leaseId);
    Integer getAmountOfPendingLeases(String userId);
    Integer getAmountOfNewLeases(String userId);
}
