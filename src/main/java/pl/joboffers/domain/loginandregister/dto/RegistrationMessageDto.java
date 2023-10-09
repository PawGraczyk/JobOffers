package pl.joboffers.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegistrationMessageDto(
        String id,
        String username,
        boolean isRegistered
) {
}
