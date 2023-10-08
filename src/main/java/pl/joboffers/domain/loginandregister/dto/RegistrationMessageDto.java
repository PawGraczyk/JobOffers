package pl.joboffers.domain.loginandregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import pl.joboffers.domain.loginandregister.RegistrationTextMessageType;

@Builder
public record RegistrationMessageDto(
        String username,
        String password,
        RegistrationTextMessageType message
) {
}
