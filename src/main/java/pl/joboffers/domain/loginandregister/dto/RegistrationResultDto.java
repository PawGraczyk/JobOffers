package pl.joboffers.domain.loginandregister.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationResultDto {

    private String id;

    private String username;

    private boolean isRegistered;

}
