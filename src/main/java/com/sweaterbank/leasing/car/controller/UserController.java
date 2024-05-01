package com.sweaterbank.leasing.car.controller;

import com.sweaterbank.leasing.car.controller.dto.responses.GetUserLeasesResponse;
import com.sweaterbank.leasing.car.model.UserLease;
import com.sweaterbank.leasing.car.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/{email}/leases")
    public ResponseEntity<GetUserLeasesResponse> getUserLeases(@PathVariable String email) {
        List<UserLease> userLeases = userService.getUserLeases(email);

        return ResponseEntity.ok(new GetUserLeasesResponse(userLeases));
    }
}
