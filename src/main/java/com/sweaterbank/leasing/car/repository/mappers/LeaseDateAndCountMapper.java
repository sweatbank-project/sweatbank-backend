package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.LeaseDateWithCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseDateAndCountMapper implements RowMapper<LeaseDateWithCount> {
    @Override
    public LeaseDateWithCount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LeaseDateWithCount(
                resultSet.getDate("creation_date"),
                resultSet.getInt("count")
        );
    }
}
