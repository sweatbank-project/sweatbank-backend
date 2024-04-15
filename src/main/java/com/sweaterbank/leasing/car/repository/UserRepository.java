package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
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

    public String saveUser(SignUpRequest request) {
        String query = """
                    INSERT INTO users(id, username, password) VALUES (:id, :username, :password);
                """;

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", 2)
                .addValue("username", request.username())
                .addValue("password", passwordEncoder.encode(request.password()));

        var user = namedParameterJdbcTemplate.query(query, params, userMapper).getFirst();

        // TODO: return ID
        return user.getUsername();
    }
}
