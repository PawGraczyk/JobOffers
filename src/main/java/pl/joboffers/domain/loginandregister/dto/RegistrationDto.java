package pl.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationDto(
        String username,
        String password) {
}
