package pl.joboffers.domain.loginandregister.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationUserDto {

    @NotEmpty(message = "{username.not.empty}")
    @NotNull(message = "{username.not.null}")
    private String username;

    @NotEmpty(message = "{password.not.empty}")
    @NotNull(message = "{password.not.null}")
    private String password;

}
