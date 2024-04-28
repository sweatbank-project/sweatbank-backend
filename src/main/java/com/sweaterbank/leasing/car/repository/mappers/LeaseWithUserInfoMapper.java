package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.ApplicationStatus;
import com.sweaterbank.leasing.car.model.BusinessAreaType;
import com.sweaterbank.leasing.car.model.EducationType;
import com.sweaterbank.leasing.car.model.HeldPositionType;
import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.MaritalStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaseWithUserInfoMapper implements RowMapper<LeasingWithUserDetail>
{
    @Override
    public LeasingWithUserDetail mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        String fullName = "%s %s".formatted(resultSet.getString("first_name"), resultSet.getString("last_name"));
        String carSeller = resultSet.getString("car_seller_name").substring(0, 1).toUpperCase() +
                resultSet.getString("car_seller_name").substring(1);

        return new LeasingWithUserDetail(
                resultSet.getString("application_id"),
                ApplicationStatus.valueOf(resultSet.getString("status").toUpperCase()),
                resultSet.getString("car_brand"),
                resultSet.getString("car_model"),
                resultSet.getInt("manufacture_year"),
                resultSet.getBigDecimal("car_cost"),
                resultSet.getInt("leasing_period"),
                carSeller,
                EducationType.valueOf(resultSet.getString("education").toUpperCase()),
                HeldPositionType.fromString(resultSet.getString("held_position")),
                resultSet.getString("job_title"),
                resultSet.getString("time_employed"),
                BusinessAreaType.fromString(resultSet.getString("employer_business_area")),
                MaritalStatus.fromString(resultSet.getString("marital_status")),
                resultSet.getInt("number_of_children"),
                resultSet.getBigDecimal("monthly_income_after_taxes"),
                new ArrayList<>(),
                resultSet.getString("personal_id"),
                fullName,
                resultSet.getString("username"),
                resultSet.getString("phone_number"),
                resultSet.getTimestamp("creation_date")
        );
    }
}
