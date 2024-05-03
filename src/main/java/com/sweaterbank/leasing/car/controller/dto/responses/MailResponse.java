package com.sweaterbank.leasing.car.controller.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.security.Timestamp;
import java.time.LocalDateTime;

public record MailResponse(
        String applicationId,
        String subject,
        String body,
        String recipient,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime sentTime
) {
}
