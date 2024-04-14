package com.sweaterbank.leasing.car.controller.dto;

// TODO: don't return accessToken, since user won't be authenticated after registering
public record SignUpResponse (String accessToken) { }
