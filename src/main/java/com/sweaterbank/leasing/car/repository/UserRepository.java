package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements UserRepositoryInterface{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
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
}
