package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeaseRequest;
import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.ObligationType;
import com.sweaterbank.leasing.car.model.UserLease;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.LeaseMapper;
import com.sweaterbank.leasing.car.repository.mappers.LeaseWithUserInfoMapper;
import com.sweaterbank.leasing.car.repository.mappers.ObligationMapper;
import com.sweaterbank.leasing.car.repository.mappers.UserLeaseMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class LeaseRepository implements LeaseRepositoryInterface
{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final LeaseMapper leaseMapper;
    private final UserLeaseMapper userLeaseMapper;
    private final LeaseWithUserInfoMapper leaseWithUserInfoMapper;
    private final ObligationMapper obligationMapper;

    private final String INITIALS_ID = "SB";
    private final int MAX_NUMBER_EXCLUSIVE_ID = 100000000;
    private final String DEFAULT_APPLICATION_ID = INITIALS_ID + "00000001";

    public LeaseRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate, LeaseMapper leaseMapper, UserLeaseMapper userLeaseMapper, LeaseWithUserInfoMapper leaseWUIMapper, ObligationMapper obligationMapper)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
        this.leaseMapper = leaseMapper;
        this.userLeaseMapper = userLeaseMapper;
        this.obligationMapper = obligationMapper;
        this.leaseWithUserInfoMapper = leaseWUIMapper;
    }

    @Override
    public void createLease(CreateLeaseRequest requestData, String leaseId) {

        String leaseApplicationId = generateApplicationId();
        Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now(ZoneId.of("GMT+3")));

        MapSqlParameterSource leasingParams = new MapSqlParameterSource()
                .addValue("id", leaseId)
                .addValue("application_id", leaseApplicationId)
                .addValue("status", ApplicationStatus.NEW.toString())
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
                .addValue("monthly_income_after_taxes", requestData.monthlyIncomeAfterTaxes())
                .addValue("creation_date",creationDate)
                .addValue("down_payment_percentage", Integer.parseInt(requestData.downPaymentPercentage()))
                .addValue("contract_fee", requestData.contractFee())
                .addValue("euribor_type", requestData.euriborType())
                .addValue("euribor_rate", requestData.euriborRate())
                .addValue("margin", requestData.margin())
                .addValue("interest_rate", requestData.interestRate())
                .addValue("monthly_payment", requestData.monthlyPayment());

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

    public List<UserLease> getUserLeases(String username) throws DataAccessException {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        return namedParameterJdbcTemplate.query(Queries.GET_USER_LEASES_QUERY, params, userLeaseMapper);
    }

    @Override
    public String generateApplicationId() {
        Integer currentCount = jdbcTemplate.queryForObject(Queries.GET_LEASES_COUNT_QUERY, Integer.class);
        if (currentCount != null) {
            currentCount++;

            int totalIntLength = String.valueOf(MAX_NUMBER_EXCLUSIVE_ID - 1).length();
            return INITIALS_ID + String.format("%0" + totalIntLength + "d", currentCount);
        }

        return DEFAULT_APPLICATION_ID;
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

    @Override
    public List<LeasingWithUserDetail> getAllLeasesWithUserDetails() throws DataAccessException
    {
        return namedParameterJdbcTemplate.query(Queries.GET_ALL_LEASINGS_WITH_USER_INFO_QUERY, resultSet ->
        {
            List<LeasingWithUserDetail> leases = new ArrayList<>();

            String leaseId = null;
            LeasingWithUserDetail currentLease = null;
            int leaseIdx = 0;
            int obligationIdx = 0;

            while (resultSet.next()) {
                if (currentLease == null || !leaseId.equals(resultSet.getString("lease_id"))) {
                    leaseId = resultSet.getString("lease_id");
                    currentLease = leaseWithUserInfoMapper.mapRow(resultSet, leaseIdx++);
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
    @Override
    public void saveUserIdWithLeaseId(String userId, String leaseId){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("lease_id", leaseId);
        namedParameterJdbcTemplate.update(Queries.SAVE_USER_ID_WITH_APPLICATION_ID_QUERY, params);
    }

    @Override
    public Integer getAmountOfPendingLeases(String userId){
        return countingLeasesStatus(Queries.GET_PENDING_STATUS_COUNT_BY_ID_QUERY, userId);
    }

    @Override
    public Integer getAmountOfNewLeases(String userId){
        return countingLeasesStatus(Queries.GET_NEW_STATUS_COUNT_BY_ID_QUERY, userId);
    }

    private Integer countingLeasesStatus(String query, String userId){
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("user_id", userId);

        return namedParameterJdbcTemplate.queryForObject(query, param, Integer.class);
    }

    public void updateLease(String leaseId, String status) throws DataAccessException {

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", leaseId)
                .addValue("status", status);

        namedParameterJdbcTemplate.update(Queries.UPDATE_LEASE_QUERY, params);
    }
}
