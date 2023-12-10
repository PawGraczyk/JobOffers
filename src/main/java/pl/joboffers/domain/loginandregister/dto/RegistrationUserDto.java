package pl.joboffers.domain.loginandregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationUserDto {
    private String username;

    private String password;

}
