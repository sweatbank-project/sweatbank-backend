package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.Obligation;
import com.sweaterbank.leasing.car.model.ObligationType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObligationMapper implements RowMapper<Obligation> {
    @Override
    public Obligation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Obligation(
                resultSet.getString("obligation_id"),
                resultSet.getString("lease_id"),
                ObligationType.fromString(resultSet.getString("obligation_type")),
                resultSet.getBigDecimal("outstanding_debt"),
                resultSet.getBigDecimal("monthly_payment")
        );
    }
}
