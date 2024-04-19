package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository implements UserRepositoryInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserMapper userMapper, PasswordEncoder passwordEncoder)
    {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectUserByEmail(String username) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        return namedParameterJdbcTemplate.query(Queries.SELECT_BY_EMAIL_QUERY, params, userMapper)
                .stream()
                .findFirst();
    }
    @Override
    public void saveUser(SignUpRequest request) {
        String generatedUUID = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(request.password());
        Timestamp expiredDate = Timestamp.valueOf(LocalDateTime.now());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", generatedUUID)
                .addValue("username", request.username())
                .addValue("personal_data_id", null)
                .addValue("password", encodedPassword)
                .addValue("role", Roles.USER.toString())
                .addValue("account_expiration_date", expiredDate)
                .addValue("account_locked", false)
                .addValue("enabled", true);

        namedParameterJdbcTemplate.update(Queries.SAVE_USER_QUERY, params);
    }
}
