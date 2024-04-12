package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.SignInRequest;
import com.sweaterbank.leasing.car.controller.dto.SignInResponse;
import com.sweaterbank.leasing.car.controller.dto.SignOutRequest;
import com.sweaterbank.leasing.car.controller.dto.SignOutResponse;
import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.SignUpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @PostMapping("signin")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest requestData) {
        return ResponseEntity.ok(new SignInResponse("jwt-token"));
    }

    @PostMapping("signout")
    public ResponseEntity<SignOutResponse> signOut(@Valid @RequestBody SignOutRequest requestData) {
        return ResponseEntity.ok(new SignOutResponse(requestData.username()));
    }

    @PostMapping("signup")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest requestData) {
        return ResponseEntity.ok(new SignUpResponse("jwt-success-token"));
    }
}
