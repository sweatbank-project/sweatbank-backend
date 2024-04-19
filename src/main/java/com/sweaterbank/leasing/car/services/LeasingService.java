package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.CreateLeasingRequest;
import com.sweaterbank.leasing.car.controller.dto.CreateLeasingResponse;
import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.repository.LeasingRepository;
import com.sweaterbank.leasing.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class LeasingService {

    @Autowired
    private LeasingRepository leasingRepository;

    public void createLeasing(CreateLeasingRequest requestData) throws HttpClientErrorException {
        leasingRepository.createLeasing(requestData);
    }

}
