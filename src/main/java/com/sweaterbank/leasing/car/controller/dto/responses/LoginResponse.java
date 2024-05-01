package com.sweaterbank.leasing.car.controller.dto.responses;

import com.sweaterbank.leasing.car.controller.dto.UserDto;

public record LoginResponse(String role, UserDto user) { }
