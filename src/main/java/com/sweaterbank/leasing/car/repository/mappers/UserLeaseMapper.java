package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.UserLease;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLeaseMapper implements RowMapper<UserLease>
{
    @Override
    public UserLease mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new UserLease(
                resultSet.getString("application_id"),
                ApplicationStatus.valueOf(resultSet.getString("status").toUpperCase()),
                resultSet.getDate("creation_date"),
                "Euribor 6",
                2f,
                new BigDecimal(150),
                new BigDecimal(30000)
        );
    }
}
