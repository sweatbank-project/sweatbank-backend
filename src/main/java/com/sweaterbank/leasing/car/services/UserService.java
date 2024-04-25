package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.RegisterRequest;
import com.sweaterbank.leasing.car.exceptions.AccountExistsException;
import com.sweaterbank.leasing.car.exceptions.NotMatchingPasswordsException;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createUser(RegisterRequest requestData) throws HttpClientErrorException.BadRequest, AccountExistsException, NotMatchingPasswordsException
    {
        Optional<User> user = userRepository.selectUserByEmail(requestData.username());
        if(user.isEmpty()){
            if(requestData.password().equals(requestData.confirmPassword())){
                userRepository.saveUser(requestData);
            } else {
                throw new NotMatchingPasswordsException();
            }
        } else {
            throw new AccountExistsException();
        }
    }
}
