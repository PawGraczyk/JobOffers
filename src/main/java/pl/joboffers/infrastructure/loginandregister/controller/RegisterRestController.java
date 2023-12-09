package pl.joboffers.infrastructure.loginandregister.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import pl.joboffers.domain.loginandregister.dto.RegistrationDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;

@AllArgsConstructor
@RestController
public class RegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@RequestBody RegistrationDto registration) {
        String encodedPassword = bCryptPasswordEncoder.encode(registration.password());
        RegistrationResultDto registrationResult = loginAndRegisterFacade.register(new RegistrationDto(registration.username(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResult);
    }
}
