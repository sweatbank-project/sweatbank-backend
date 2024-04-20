package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.model.*;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.LeaseMapper;
import com.sweaterbank.leasing.car.repository.mappers.ObligationMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LeaseRepository implements LeaseRepositoryInterface
{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final LeaseMapper leaseMapper;
    private final ObligationMapper obligationMapper;

    public LeaseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, LeaseMapper leaseMapper, ObligationMapper obligationMapper)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.leaseMapper = leaseMapper;
        this.obligationMapper = obligationMapper;
    }

    @Override
    public void createLease(CreateLeaseRequest requestData) {
        String leaseId = UUID.randomUUID().toString();

        MapSqlParameterSource leasingParams = new MapSqlParameterSource()
                .addValue("id", leaseId)
                .addValue("status", Status.PENDING.toString()) // Assuming default status is "NEW"
                .addValue("car_brand", requestData.makes())
                .addValue("car_model", requestData.models())
                .addValue("manufacture_year", Integer.parseInt(requestData.yearOfManufacture()))
                .addValue("car_cost", requestData.costOfTheVehicle())
                .addValue("leasing_period", Integer.parseInt(requestData.leasingPeriod()))
                .addValue("car_seller_name", requestData.sellerName())
                .addValue("education", requestData.education())
                .addValue("held_position", requestData.positionHeld())
                .addValue("job_title", requestData.jobTitle())
                .addValue("time_employed", Integer.parseInt(requestData.timeEmployed()))
                .addValue("employer_business_area", requestData.businessAreaOfYourEmployer())
                .addValue("marital_status", requestData.maritalStatus())
                .addValue("number_of_children", requestData.numberOfChildren())
                .addValue("monthly_income_after_taxes", requestData.monthlyIncomeAfterTaxes());

        namedParameterJdbcTemplate.update(Queries.SAVE_LEASING_QUERY, leasingParams);

        createObligations(requestData, leaseId);
    }

    @Override
    public void createObligations(CreateLeaseRequest requestData, String leaseId) {
        namedParameterJdbcTemplate.batchUpdate(Queries.SAVE_OBLIGATIONS_QUERY,
                getObligationsBatchParameters(requestData, leaseId));
    }

    public SqlParameterSource[] getObligationsBatchParameters(CreateLeaseRequest requestData,
                                                              String leaseId) {
        List<SqlParameterSource> params = new ArrayList<>();

        if (requestData.obligations().equalsIgnoreCase("YES")) {
            if (requestData.customerLoansOutstanding() != null) {
                String customerObligationId = UUID.randomUUID().toString();

                SqlParameterSource customerParams = new MapSqlParameterSource()
                        .addValue("id", customerObligationId)
                        .addValue("leasing_id", leaseId)
                        .addValue("obligation_type", ObligationType.CUSTOMER.toString())
                        .addValue("outstanding_debt", requestData.customerLoansOutstanding())
                        .addValue("monthly_payment", requestData.customerLoansMonthlyPayment());

                params.add(customerParams);
            }

            if (requestData.carLeaseOutstanding() != null) {
                String carLeaseObligationId = UUID.randomUUID().toString();

                SqlParameterSource carLeaseParams = new MapSqlParameterSource()
                        .addValue("id", carLeaseObligationId)
                        .addValue("leasing_id", leaseId)
                        .addValue("obligation_type", ObligationType.CAR_LEASE.toString())
                        .addValue("outstanding_debt", requestData.carLeaseOutstanding())
                        .addValue("monthly_payment", requestData.carLeaseMonthlyPayment());

                params.add(carLeaseParams);
            }

            if (requestData.creditCardOutstanding() != null) {
                String creditCardObligationId = UUID.randomUUID().toString();

                SqlParameterSource creditCardParams = new MapSqlParameterSource()
                        .addValue("id", creditCardObligationId)
                        .addValue("leasing_id", leaseId)
                        .addValue("obligation_type", ObligationType.CREDIT_CARD.toString())
                        .addValue("outstanding_debt", requestData.creditCardOutstanding())
                        .addValue("monthly_payment", requestData.creditCardMonthlyPayment());

                params.add(creditCardParams);
            }

            if (requestData.mortgageOutstanding() != null) {
                String mortgageObligationId = UUID.randomUUID().toString();

                SqlParameterSource mortgageParams = new MapSqlParameterSource()
                        .addValue("id", mortgageObligationId)
                        .addValue("leasing_id", leaseId)
                        .addValue("obligation_type", ObligationType.MORTGAGE.toString())
                        .addValue("outstanding_debt", requestData.mortgageOutstanding())
                        .addValue("monthly_payment", requestData.mortgageMonthlyPayment());

                params.add(mortgageParams);
            }

            if (requestData.otherCreditsOutstanding() != null) {
                String otherObligationsId = UUID.randomUUID().toString();

                SqlParameterSource otherCreditsParams = new MapSqlParameterSource()
                        .addValue("id", otherObligationsId)
                        .addValue("leasing_id", leaseId)
                        .addValue("obligation_type", ObligationType.OTHER_CREDITS.toString())
                        .addValue("outstanding_debt", requestData.otherCreditsOutstanding())
                        .addValue("monthly_payment", requestData.otherCreditsMonthlyPayment());

                params.add(otherCreditsParams);
            }
        }

        return params.toArray(new SqlParameterSource[0]);
    }

    @Override
    public Optional<Leasing> getLeaseById(String leaseId)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", leaseId);

        return namedParameterJdbcTemplate.query(Queries.GET_LEASING_QUERY, params, leaseMapper)
                .stream()
                .findFirst();
    }

    @Override
    public List<Leasing> getAllLeases() throws DataAccessException
    {
        return namedParameterJdbcTemplate.query(Queries.GET_ALL_LEASING_QUERY, resultSet ->
        {
            List<Leasing> leases = new ArrayList<>();

            String leaseId = null;
            Leasing currentLease = null;
            int leaseIdx = 0;
            int obligationIdx = 0;

            while (resultSet.next()) {
                if (currentLease == null || !leaseId.equals(resultSet.getString("id"))) {
                    leaseId = resultSet.getString("id");
                    currentLease = leaseMapper.mapRow(resultSet, leaseIdx++);
                    obligationIdx = 0;
                    leases.add(currentLease);
                }

                if (currentLease != null) {
                    currentLease.addObligation(obligationMapper.mapRow(resultSet, obligationIdx++));
                }
            }

            return leases;
        });
    }
}
