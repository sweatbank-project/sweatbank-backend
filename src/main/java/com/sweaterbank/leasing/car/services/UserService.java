package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.LoginRequest;
import com.sweaterbank.leasing.car.controller.dto.RegisterRequest;
import com.sweaterbank.leasing.car.controller.dto.UserDto;
import com.sweaterbank.leasing.car.exceptions.AccountExistsException;
import com.sweaterbank.leasing.car.exceptions.NotMatchingPasswordsException;
import com.sweaterbank.leasing.car.model.Roles;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    public User loadUserByUsername(String email) throws UsernameNotFoundException
    {
        Optional<User> receivedUser = userRepository.selectUserByEmail(email);
        if (receivedUser.isPresent()){
            return receivedUser.get();
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND_MSG.formatted(email));
        }
    }
    public String getUserIdByUsername(String email) throws UsernameNotFoundException{
        String userId = userRepository.selectUserIdByEmail(email);
        if(!userId.isEmpty() || !userId.isBlank()){
            return userId;
        }else {
            throw new UsernameNotFoundException("Invalid provided credentials");
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

    public Authentication authenticateUser(LoginRequest requestData, AuthenticationManager authenticationManager) {
        return authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                requestData.username(), requestData.password()
                        )
                );
    }

    public String getUserRole(User user) {
        String role = Roles.USER.toString();
        Optional<? extends GrantedAuthority> optionalAuthority = user.getAuthorities().stream().findFirst();
        if (optionalAuthority.isPresent()) {
            role = optionalAuthority.get().getAuthority();
        }
        return role;
    }

    public UserDto createUserDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName(),
                user.getUsername(), user.getPhoneNumber(), user.getAddress(),
                user.getBirthdate());
    }
}
