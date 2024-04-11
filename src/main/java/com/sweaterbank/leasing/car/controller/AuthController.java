package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.SignInRequest;
import com.sweaterbank.leasing.car.controller.dto.SignInResponse;
import com.sweaterbank.leasing.car.controller.dto.SignOutRequest;
import com.sweaterbank.leasing.car.controller.dto.SignOutResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest requestData) {
        return ResponseEntity.ok(new SignInResponse("jwt-token"));
    }

    @RequestMapping()
    public ResponseEntity<SignOutResponse> signOut(@Valid @RequestBody SignOutRequest requestData) {
        return ResponseEntity.ok(new SignOutResponse("username"));
    }
}
