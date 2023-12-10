package pl.joboffers.infrastructure.loginandregister.controller.error.token;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message, HttpStatus status) {
}
