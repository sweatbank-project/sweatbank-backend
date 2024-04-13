package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.exceptions.UserNotFoundException;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> receivedUser = userRepository.selectUserByEmail(email);
        if(receivedUser.isPresent()){
            return receivedUser.get();
        }else{
            throw new UserNotFoundException();
        }
    }
}
