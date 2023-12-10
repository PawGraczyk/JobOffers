package pl.joboffers.infrastructure.loginandregister.controller.error.register;

import org.springframework.http.HttpStatus;

public record RegisterErrorResponse(String message, HttpStatus status) {
}
