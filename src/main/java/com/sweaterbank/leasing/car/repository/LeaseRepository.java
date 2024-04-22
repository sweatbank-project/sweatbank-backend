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

import java.math.BigDecimal;
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
                .addValue("status", Status.PENDING.toString())
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
                SqlParameterSource customerParams = getObligationParams(leaseId, ObligationType.CUSTOMER,
                        requestData.customerLoansOutstanding(), requestData.customerLoansMonthlyPayment());

                params.add(customerParams);
            }

            if (requestData.carLeaseOutstanding() != null) {
                SqlParameterSource carLeaseParams = getObligationParams(leaseId, ObligationType.CAR_LEASE,
                        requestData.carLeaseOutstanding(), requestData.carLeaseMonthlyPayment());

                params.add(carLeaseParams);
            }

            if (requestData.creditCardOutstanding() != null) {
                SqlParameterSource creditCardParams = getObligationParams(leaseId, ObligationType.CREDIT_CARD,
                        requestData.creditCardOutstanding(), requestData.creditCardMonthlyPayment());

                params.add(creditCardParams);
            }

            if (requestData.mortgageOutstanding() != null) {
                SqlParameterSource mortgageParams = getObligationParams(leaseId, ObligationType.MORTGAGE,
                        requestData.mortgageOutstanding(), requestData.mortgageMonthlyPayment());

                params.add(mortgageParams);
            }

            if (requestData.otherCreditsOutstanding() != null) {
                SqlParameterSource otherCreditsParams = getObligationParams(leaseId, ObligationType.OTHER_CREDITS,
                        requestData.otherCreditsOutstanding(), requestData.otherCreditsMonthlyPayment());

                params.add(otherCreditsParams);
            }
        }

        return params.toArray(new SqlParameterSource[0]);
    }

    private SqlParameterSource getObligationParams(String leaseId,
                                                           ObligationType obligationType,
                                                           BigDecimal obligationOutstanding,
                                                           BigDecimal monthlyPayment) {
        String obligationId = UUID.randomUUID().toString();

        return new MapSqlParameterSource()
                .addValue("id", obligationId)
                .addValue("leasing_id", leaseId)
                .addValue("obligation_type", obligationType.toString())
                .addValue("outstanding_debt", obligationOutstanding)
                .addValue("monthly_payment", monthlyPayment);
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

                if (currentLease != null && resultSet.getString("obligation_id") != null) {
                    currentLease.addObligation(obligationMapper.mapRow(resultSet, obligationIdx++));
                }
            }

            return leases;
        });
    }
}
