package pl.joboffers.infrastructure.loginandregister.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;

@AllArgsConstructor
@RestController
public class RegisterRestController {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    private final PasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> register(@RequestBody @Valid RegistrationUserDto registration) {
        String encodedPassword = bCryptPasswordEncoder.encode(registration.getPassword());
        RegistrationResultDto registrationResult = loginAndRegisterFacade.register(new RegistrationUserDto(registration.getUsername(), encodedPassword));
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResult);
    }
}
