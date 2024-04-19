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
                resultSet.getString("id"),
                resultSet.getString("leasing_id"),
                ObligationType.fromString(resultSet.getString("type_of_obligation")),
                resultSet.getBigDecimal("outstanding_debt"),
                resultSet.getBigDecimal("monthly_payment")
        );
    }
}
