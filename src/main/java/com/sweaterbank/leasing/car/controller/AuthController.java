package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.UserDto;
import com.sweaterbank.leasing.car.controller.dto.requests.LoginRequest;
import com.sweaterbank.leasing.car.controller.dto.requests.RegisterRequest;
import com.sweaterbank.leasing.car.controller.dto.responses.LoginResponse;
import com.sweaterbank.leasing.car.controller.dto.responses.RegisterResponse;
import com.sweaterbank.leasing.car.exceptions.AccountExistsException;
import com.sweaterbank.leasing.car.exceptions.NotMatchingPasswordsException;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.services.JwtService;
import com.sweaterbank.leasing.car.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService)  {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestData) throws BadCredentialsException
    {
        try {
            Authentication authenticate = userService.authenticateUser(requestData, authenticationManager);
            User user = (User) authenticate.getPrincipal();
            String role = userService.getUserRole(user);
            UserDto userDto = userService.createUserDto(user);
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(new LoginResponse(role, userDto));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest requestData) throws AccountExistsException, NotMatchingPasswordsException, ParseException
    {
        userService.createUser(requestData);

        return ResponseEntity.ok().build();
    }
}
