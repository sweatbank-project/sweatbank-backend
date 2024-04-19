package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.CreateLeasingRequest;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.Obligation;
import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.Status;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.LeasingMapper;
import com.sweaterbank.leasing.car.repository.mappers.ObligationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class LeasingRepository implements LeasingRepositoryInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final LeasingMapper leasingMapper;
    private final ObligationMapper obligationMapper;

    public LeasingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, LeasingMapper leasingMapper, ObligationMapper obligationMapper)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.leasingMapper = leasingMapper;
        this.obligationMapper = obligationMapper;
    }

    @Override
    public void createLeasing(CreateLeasingRequest requestData) {
        String leasingId = UUID.randomUUID().toString();

        MapSqlParameterSource leasingParams = new MapSqlParameterSource()
                .addValue("id", leasingId)
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

        createObligations(requestData, leasingId);
    }

    @Override
    public void createObligations(CreateLeasingRequest requestData, String leasingId) {
        namedParameterJdbcTemplate.batchUpdate(Queries.SAVE_OBLIGATIONS_QUERY,
                getObligationsBatchParameters(requestData, leasingId));
    }

    public SqlParameterSource[] getObligationsBatchParameters(CreateLeasingRequest requestData,
                                                                     String leasingId) {
        List<SqlParameterSource> params = new ArrayList<>();

        if (requestData.obligations().equalsIgnoreCase("YES")) {
            // TODO: add customer, car lease, credit card, mortgage and other obligations
        }

        return params.toArray(new SqlParameterSource[0]);
    }

    @Override
    public Optional<Leasing> getLeasingById(String leasingId)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", leasingId);

        return namedParameterJdbcTemplate.query(Queries.GET_LEASING_QUERY, params, leasingMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Collection<Leasing> getAllLeasings(int limit, int offset)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        return namedParameterJdbcTemplate.query(Queries.GET_ALL_LEASING_QUERY, params, leasingMapper);
    }

    @Override
    public Collection<Obligation> getObligationsForLeasing(String leasingId)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("leasing_id", leasingId);

        return namedParameterJdbcTemplate.query(Queries.GET_OBLIGATIONS_FOR_LEASING_QUERY, params, obligationMapper);
    }


}
