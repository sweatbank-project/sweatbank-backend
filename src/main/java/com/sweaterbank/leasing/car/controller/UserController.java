package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.SignUpRequest;
import com.sweaterbank.leasing.car.controller.dto.SignUpResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("register")
    public ResponseEntity<SignUpResponse> register(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.ok(new SignUpResponse("jwt-test-token"));
    }
}
