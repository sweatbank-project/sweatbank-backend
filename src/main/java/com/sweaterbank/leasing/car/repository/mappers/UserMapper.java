package com.sweaterbank.leasing.car.repository.mappers;

import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(
                resultSet.getString("id"),
                resultSet.getString("username"),
                resultSet.getString("phone_number"),
                resultSet.getString("personal_id"),
                resultSet.getString("password"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getDate("birth_date"),
                resultSet.getString("address"),
                Roles.valueOf(resultSet.getString("role").toUpperCase()),
                resultSet.getTimestamp("account_expiration_date"),
                resultSet.getBoolean("account_locked"),
                resultSet.getBoolean("enabled")
        );
    }
}
