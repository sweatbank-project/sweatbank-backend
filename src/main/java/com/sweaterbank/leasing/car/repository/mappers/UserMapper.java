package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getString("id"),
                resultSet.getString("personal_data_id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                Roles.valueOf(resultSet.getString("role").toUpperCase()),
                resultSet.getTimestamp("account_expiration_date"),
                resultSet.getBoolean("account_locked"),
                resultSet.getBoolean("enabled")
        );
    }
}
