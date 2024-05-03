package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.UserLease;
import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLeaseMapper implements RowMapper<UserLease>
{
    @Override
    public UserLease mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new UserLease(
                resultSet.getString("application_id"),
                ApplicationStatus.fromString(resultSet.getString("status")),
                resultSet.getDate("creation_date"),
                resultSet.getString("euribor_type"),
                resultSet.getBigDecimal("euribor_rate"),
                resultSet.getBigDecimal("monthly_payment"),
                resultSet.getBigDecimal("car_cost")
        );
    }
}
