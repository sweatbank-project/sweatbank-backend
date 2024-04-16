package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.SignInRequest;
import com.sweaterbank.leasing.car.controller.dto.SignInResponse;
import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.SignUpResponse;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.services.JwtService;
import com.sweaterbank.leasing.car.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
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
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest requestData) throws BadCredentialsException
    {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    requestData.username(), requestData.password()
                            )
                    );
            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtService.generateToken(user)
                    )
                    .body(new SignInResponse("Authentication successful."));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignUpRequest requestData) {
        userService.createUser(requestData);

        return ResponseEntity.ok(new SignUpResponse("User created"));
    }
}
