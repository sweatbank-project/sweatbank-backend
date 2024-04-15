package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.sql.Timestamp;
import java.util.Date;
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
    public Optional<User> selectUserByEmail(String email){
        String query = """
                    SELECT * from user
                    WHERE email = :email
                """;
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email);

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
                .addValue("personal_data_id", "2023-01-23")
                .addValue("username", request.username())
                .addValue("password", passwordEncoder.encode(request.password()))
                .addValue("role", Roles.USER)
                .addValue("account_expiration_date", new Timestamp(new Date().getTime()).getTime())
                .addValue("account_locked", false)
                .addValue("enabled", true);

        namedParameterJdbcTemplate.query(query, params, userMapper);

    }
}
