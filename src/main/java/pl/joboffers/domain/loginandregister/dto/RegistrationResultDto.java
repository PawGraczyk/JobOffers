package pl.joboffers.domain.loginandregister.dto;



import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationResultDto {

    private String id;

    private String username;

    private boolean isRegistered;

}
