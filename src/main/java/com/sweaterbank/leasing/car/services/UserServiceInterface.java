package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.UserRequest;
import com.sweaterbank.leasing.car.controller.dto.UserResponse;
import com.sweaterbank.leasing.car.exceptions.UserNotFoundException;
import com.sweaterbank.leasing.car.model.User;

public interface UserServiceInterface {

    User getUserByEmail(String email) throws UserNotFoundException;

}
