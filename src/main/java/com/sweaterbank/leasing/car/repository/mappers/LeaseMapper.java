package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.BusinessAreaType;
import com.sweaterbank.leasing.car.model.EducationType;
import com.sweaterbank.leasing.car.model.EuriborType;
import com.sweaterbank.leasing.car.model.HeldPositionType;
import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.MaritalStatus;
import com.sweaterbank.leasing.car.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaseMapper implements RowMapper<Leasing>
{
    @Override
    public Leasing mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new Leasing(
                resultSet.getString("id"),
                resultSet.getString("application_id"),
                ApplicationStatus.valueOf(resultSet.getString("status").toUpperCase()),
                resultSet.getString("car_brand"),
                resultSet.getString("car_model"),
                resultSet.getInt("manufacture_year"),
                resultSet.getBigDecimal("car_cost"),
                resultSet.getInt("leasing_period"),
                resultSet.getString("car_seller_name"),
                EducationType.valueOf(resultSet.getString("education").toUpperCase()),
                HeldPositionType.fromString(resultSet.getString("held_position")),
                resultSet.getString("job_title"),
                resultSet.getString("time_employed"),
                BusinessAreaType.fromString(resultSet.getString("employer_business_area")),
                MaritalStatus.fromString(resultSet.getString("marital_status")),
                resultSet.getInt("number_of_children"),
                resultSet.getBigDecimal("monthly_income_after_taxes"),
                resultSet.getInt("down_payment_percentage"),
                resultSet.getBigDecimal("contract_fee"),
                EuriborType.valueOf(resultSet.getString("euribor_type").toUpperCase()),
                resultSet.getBigDecimal("euribor_rate"),
                resultSet.getBigDecimal("margin"),
                resultSet.getBigDecimal("interest_rate"),
                resultSet.getBigDecimal("monthly_payment"),
                new ArrayList<>()
        );
    }
}
