package pl.joboffers.infrastructure.security.error;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SecurityErrorHandler {

    @ResponseBody
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public SecurityErrorResponse handleSecurityErrors(){
        final String message = "Invalid token";
        return new SecurityErrorResponse(message, HttpStatus.FORBIDDEN);
    }
}
