package com.sweaterbank.leasing.car.controller.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotEmpty
        @NotBlank
        @NotNull(message = "Username may not be null")
        @Email(message = "Email must be in a valid form: example@mymail.com", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
        String username,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Field phone number may not be null")
        String phoneNumber,
        @Size(min = 11, max = 11, message = "Personal ID must be of size 11")
        @Pattern(regexp = "^[0-9]+$", message = "Personal ID must include only number from 1 to 9")
        @NotEmpty
        @NotBlank
        @NotNull(message = "Personal ID may not be null")
        String personalId,
        @NotEmpty
        @NotBlank
        @Size(min = 6, message = "The password must be at least 6 characters long")
        @NotNull(message = "Password may not be null")
        @Pattern(regexp = "^[^\\s-]+$", message = "Password cannot contain spaces and dashes")
        String password,
        @NotEmpty
        @NotBlank
        @NotNull(message = "First name may not be null") String firstName,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Last name may not be null") String lastName,

        @NotEmpty
        @NotBlank
        @NotNull(message = "Birthdate may not be null")
        @Pattern(regexp = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "Birthdate must be " +
                "between 1900 and 2099 and in the format of yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        String birthDate,
        @NotEmpty
        @NotBlank
        @NotNull(message = "Address may not be null")
        String address,

        @NotNull
        @NotEmpty
        @NotBlank
        String confirmPassword
) { }
