package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.LeasingWithUserDetail;
import com.sweaterbank.leasing.car.model.enums.ApplicationStatus;
import com.sweaterbank.leasing.car.model.enums.AutomationStatus;
import com.sweaterbank.leasing.car.model.enums.BusinessAreaType;
import com.sweaterbank.leasing.car.model.enums.EducationType;
import com.sweaterbank.leasing.car.model.enums.EuriborType;
import com.sweaterbank.leasing.car.model.enums.HeldPositionType;
import com.sweaterbank.leasing.car.model.enums.MaritalStatus;
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
                ApplicationStatus.fromString(resultSet.getString("status")),
                AutomationStatus.fromString(resultSet.getString("automation_status")),
                resultSet.getString("car_brand"),
                resultSet.getString("car_model"),
                resultSet.getInt("manufacture_year"),
                resultSet.getBigDecimal("car_cost"),
                resultSet.getBigDecimal("down_payment"),
                resultSet.getInt("leasing_period"),
                carSeller,
                EducationType.fromString(resultSet.getString("education")),
                HeldPositionType.fromString(resultSet.getString("held_position")),
                resultSet.getString("job_title"),
                resultSet.getString("time_employed"),
                BusinessAreaType.fromString(resultSet.getString("employer_business_area")),
                MaritalStatus.fromString(resultSet.getString("marital_status")),
                resultSet.getInt("number_of_children"),
                resultSet.getBigDecimal("monthly_income_after_taxes"),
                resultSet.getInt("down_payment_percentage"),
                resultSet.getBigDecimal("contract_fee"),
                EuriborType.fromString(resultSet.getString("euribor_type")),
                resultSet.getBigDecimal("euribor_rate"),
                resultSet.getBigDecimal("margin"),
                resultSet.getBigDecimal("interest_rate"),
                resultSet.getBigDecimal("leasing_monthly_payment"),
                new ArrayList<>(),
                resultSet.getString("personal_id"),
                fullName,
                resultSet.getString("username"),
                resultSet.getString("phone_number"),
                resultSet.getTimestamp("creation_date")
        );
    }
}
