package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.MailData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailDataMapper implements RowMapper<MailData> {

    @Override
    public MailData mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new MailData(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getBigDecimal("car_cost"),
                resultSet.getBigDecimal("down_payment"),
                resultSet.getBigDecimal("margin"),
                resultSet.getString("euribor_type"),
                resultSet.getInt("leasing_period")
        );
    }
}
