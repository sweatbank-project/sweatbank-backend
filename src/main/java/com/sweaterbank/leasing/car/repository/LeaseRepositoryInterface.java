package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.requests.CreateLeaseRequest;
import com.sweaterbank.leasing.car.exceptions.PendingLeasesException;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;
import com.sweaterbank.leasing.car.model.enums.AutomationStatus;
import com.sweaterbank.leasing.car.model.MailData;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.mappers.MailDataMapper;

import java.util.Collection;
import java.util.Optional;

public interface LeaseRepositoryInterface
{
    void createLease(CreateLeaseRequest requestData, String leaseId, ApplicationStatus applicationStatus,
                     AutomationStatus automationStatus) throws PendingLeasesException;
    void createObligations(CreateLeaseRequest requestData, String leaseId);
    String generateApplicationId();
    Collection<LeasingWithUserDetail> getAllLeasesWithUserDetails();
    void saveUserIdWithLeaseId(String userId, String leaseId);
    Integer getAmountOfPendingLeases(String userId);
    Integer getAmountOfNewLeases(String userId);

    Optional<MailData> getDataForMail(String application_id);
}
