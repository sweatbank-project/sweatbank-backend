package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.repository.LeaseRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class LeaseService
{

    private final LeaseRepository leaseRepository;

    public LeaseService(LeaseRepository leaseRepository)
    {
        this.leaseRepository = leaseRepository;
    }

    public void createLease(CreateLeaseRequest requestData) throws HttpClientErrorException {
        leaseRepository.createLease(requestData);
    }

    public List<Leasing> getLeases() throws DataAccessException {
        return leaseRepository.getAllLeases();
    }
}
