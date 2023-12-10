package pl.joboffers.infrastructure.loginandregister.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRequestDto(@NotBlank(message = "{username.not.empty}") String username,
                              @NotBlank(message = "{password.not.empty}") String password) {
}
