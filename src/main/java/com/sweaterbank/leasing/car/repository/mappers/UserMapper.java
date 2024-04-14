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
                resultSet.getString("personalDataId"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getObject("role", Roles.class),
                resultSet.getTimestamp("accountExpirationDate"),
                resultSet.getBoolean("accountLocked"),
                resultSet.getBoolean("enabled")
        );
    }
}
