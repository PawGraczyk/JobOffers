package pl.joboffers.infrastructure.security.error;

import org.springframework.http.HttpStatus;

public record SecurityErrorResponse(
        String message,
        HttpStatus status
) {
}
