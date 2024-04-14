package com.sweaterbank.leasing.car.repository;

import com.sweaterbank.leasing.car.model.User;

import java.util.Optional;

public interface UserRepositoryInterface {

    Optional<User> selectUserByEmail(String email);
}
