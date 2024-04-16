package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    private final static String USER_NOT_FOUND_MSG = "Could not find user with email: %s.";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<User> receivedUser = userRepository.selectUserByEmail(email);
        if (receivedUser.isPresent()){
            return receivedUser.get();
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND_MSG.formatted(email));
        }
    }

    public void createUser(SignUpRequest requestData) throws DataAccessException {
        // TODO: email, password validation

        userRepository.saveUser(requestData);
    }
}
