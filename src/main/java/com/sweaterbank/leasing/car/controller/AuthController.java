package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.SignInRequest;
import com.sweaterbank.leasing.car.controller.dto.SignInResponse;
import com.sweaterbank.leasing.car.controller.dto.SignOutRequest;
import com.sweaterbank.leasing.car.controller.dto.SignOutResponse;
import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.SignUpResponse;
import com.sweaterbank.leasing.car.model.User;
import com.sweaterbank.leasing.car.repository.UserRepository;
import com.sweaterbank.leasing.car.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    // TODO: instead of repo, use Service
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService ;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository)  {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest requestData) throws BadCredentialsException
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

    @PostMapping("logout")
    public ResponseEntity<SignOutResponse> signOut(@Valid @RequestBody SignOutRequest requestData) {
        return ResponseEntity.ok(new SignOutResponse(requestData.username()));
    }

    @PostMapping("register")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest requestData) {
        userRepository.saveUser(requestData);

        return ResponseEntity.ok(new SignUpResponse("User created"));
    }
}
