package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.Obligation;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.LeasingMapper;
import com.sweaterbank.leasing.car.repository.mappers.ObligationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

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
