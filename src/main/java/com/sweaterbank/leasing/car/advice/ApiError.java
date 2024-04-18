package com.sweaterbank.leasing.car.advice;

import java.util.List;

public record ApiError (List<String> errors) { }
