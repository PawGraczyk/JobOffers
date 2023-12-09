package pl.joboffers.infrastructure.loginandregister.controller.dto;

public record JwtResponseDto(
        String username,
        String token
) {
}
