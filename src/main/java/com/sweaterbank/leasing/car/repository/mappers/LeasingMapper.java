package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.Leasing;
import com.sweaterbank.leasing.car.model.LeasingStatus;
import com.sweaterbank.leasing.car.model.Obligation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class LeasingMapper implements RowMapper<Leasing>
{
    @Override
    public Leasing mapRow(ResultSet resultSet, int rowNum) throws SQLException
    {
        return new Leasing(
                resultSet.getString("id"),
                LeasingStatus.valueOf(resultSet.getString("status").toUpperCase()),
                resultSet.getString("car_brand"),
                resultSet.getString("car_model"),
                resultSet.getInt("manufacture_year"),
                resultSet.getBigDecimal("car_cost"),
                resultSet.getInt("leasing_period"),
                resultSet.getString("car_seller_name"),
                resultSet.getString("education"),
                resultSet.getString("held_position"),
                resultSet.getString("job_title"),
                resultSet.getString("time_employed"),
                resultSet.getString("employer_business_area"),
                resultSet.getString("marital_status"),
                resultSet.getInt("number_of_children"),
                resultSet.getBigDecimal("monthly_income_after_taxes")
        );
    }
}
