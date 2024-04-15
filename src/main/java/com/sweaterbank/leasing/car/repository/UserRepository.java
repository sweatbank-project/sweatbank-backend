package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Repository
public class UserRepository implements UserRepositoryInterface{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<User> selectUserByEmail(String username) {
        String query = """
                    SELECT * from users
                    WHERE username = :username
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        return namedParameterJdbcTemplate.query(query, params, new UserMapper())
                .stream()
                .findFirst();
    }

    public void saveUser(SignUpRequest request) {
        String query = """
                    INSERT INTO users(id, username, personal_data_id, password, role, account_locked, enabled)
                    VALUES (:id, :username, :personal_data_id, :password, :role, :account_locked, :enabled);
                """;

        String generatedUUID = UUID.randomUUID().toString();

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", generatedUUID)
                .addValue("username", request.username())
                .addValue("personal_data_id", null)
                .addValue("password", passwordEncoder.encode(request.password()))
                .addValue("role", Roles.USER.toString())
                .addValue("account_locked", false)
                .addValue("enabled", true);

        namedParameterJdbcTemplate.update(query, params);
    }
}
