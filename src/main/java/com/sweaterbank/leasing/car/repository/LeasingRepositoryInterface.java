package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeasingRequest;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.Obligation;

import java.util.Collection;
import java.util.Optional;

public interface LeasingRepositoryInterface {

    void createLeasing(CreateLeasingRequest requestData);
    Optional<Leasing> getLeasingById(String leasingId);
    Collection<Leasing> getAllLeasings(int limit, int offset);
    Collection<Obligation> getObligationsForLeasing(String leasingId);
}
