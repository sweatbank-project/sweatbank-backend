package com.sweaterbank.leasing.car.repository;

import com.fasterxml.uuid.Generators;
import com.sweaterbank.leasing.car.controller.dto.requests.RegisterRequest;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.model.enums.Role;
import com.sweaterbank.leasing.car.repository.contants.Queries;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
    public String selectUserIdByEmail(String username){
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", username);

        return namedParameterJdbcTemplate.queryForObject(Queries.SELECT_USER_ID_BY_EMAIL_QUERY, params, String.class);
    }

    @Override
    public void saveUser(RegisterRequest request) throws ParseException
    {
        String generatedUUID = Generators.timeBasedEpochGenerator().generate().toString();
        String encodedPassword = passwordEncoder.encode(request.password());
        Timestamp expiredDate = Timestamp.valueOf(LocalDateTime.now());
        Date birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.birthDate());

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", generatedUUID)
                .addValue("username", request.username())
                .addValue("phone_number",request.phoneNumber())
                .addValue("personal_id", request.personalId())
                .addValue("password", encodedPassword)
                .addValue("first_name", request.firstName())
                .addValue("last_name", request.lastName())
                .addValue("birth_date", birthDate)
                .addValue("address", request.address())
                .addValue("role", Role.USER.toString())
                .addValue("account_expiration_date", expiredDate)
                .addValue("account_locked", false)
                .addValue("enabled", true);

        namedParameterJdbcTemplate.update(Queries.SAVE_USER_QUERY, params);
    }
}
