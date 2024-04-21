package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.model.Leasing;

import java.util.Collection;
import java.util.Optional;

public interface LeaseRepositoryInterface
{

    void createLease(CreateLeaseRequest requestData);
    void createObligations(CreateLeaseRequest requestData, String leaseId);
    Optional<Leasing> getLeaseById(String leaseId);
    Collection<Leasing> getAllLeases();
}
