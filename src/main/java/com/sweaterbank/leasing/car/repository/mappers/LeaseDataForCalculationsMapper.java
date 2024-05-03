package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.LeaseDataForCalculations;
import com.sweaterbank.leasing.car.model.enums.MaritalStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaseDataForCalculationsMapper implements RowMapper<LeaseDataForCalculations> {
    @Override
    public LeaseDataForCalculations mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LeaseDataForCalculations(
                resultSet.getBigDecimal("monthly_payment"),
                resultSet.getBigDecimal("monthly_income_after_taxes"),
                resultSet.getInt("number_of_children"),
                MaritalStatus.fromString(resultSet.getString("marital_status")),
                resultSet.getBigDecimal("obligation_payment")
        );
    }
}
