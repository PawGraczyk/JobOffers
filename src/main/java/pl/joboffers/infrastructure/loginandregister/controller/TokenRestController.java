package pl.joboffers.infrastructure.loginandregister.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.joboffers.infrastructure.loginandregister.controller.dto.TokenRequestDto;
import pl.joboffers.infrastructure.security.jwt.JwtAuthenticatorFacade;

@RestController
@AllArgsConstructor
public class TokenRestController {

    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping("/token")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@Valid @RequestBody TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }
}
