package com.sweaterbank.leasing.car.controller.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MailRequest(
        @NotBlank
        String recipient,
        @NotBlank
        String subject,
        @NotBlank
        String body,
        String applicationId
) {
}
