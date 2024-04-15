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
import java.util.Optional;

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
                    INSERT INTO users
                    VALUES (:id, :personal_data_id, :username, :password, :role, :account_expiration_date, :account_locked, :enabled);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", "hdskjfhasd")
                .addValue("username", request.username())
                .addValue("personal_data_id", null)
                .addValue("password", passwordEncoder.encode(request.password()))
                .addValue("role", Roles.USER.toString())
                .addValue("account_expiration_date", Timestamp.valueOf("2004-10-19 10:23:54"), Types.TIMESTAMP)
                .addValue("account_locked", false)
                .addValue("enabled", true);

        namedParameterJdbcTemplate.query(query, params, userMapper);

    }
}
