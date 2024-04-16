package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.RegisterRequest;
import com.sweaterbank.leasing.car.controller.dto.RegisterResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest signUpRequest) {
        return ResponseEntity.ok(new RegisterResponse("jwt-test-token"));
    }
}
