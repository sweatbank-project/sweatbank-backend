package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.exceptions.UserNotFoundException;
import com.sweaterbank.leasing.car.model.User;

public interface UserServiceInterface {

    User getUserByEmail(String email) throws UserNotFoundException;

}
