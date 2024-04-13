package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.UserRequest;
import com.sweaterbank.leasing.car.controller.dto.UserResponse;
import com.sweaterbank.leasing.car.exceptions.UserNotFoundException;

public interface UserServiceInterface {

    UserResponse getUserById(String id) throws UserNotFoundException;
    void createUser(UserRequest userRequest);

}
