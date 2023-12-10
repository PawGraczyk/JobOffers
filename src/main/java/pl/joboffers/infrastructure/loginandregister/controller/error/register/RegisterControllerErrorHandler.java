package pl.joboffers.infrastructure.loginandregister.controller.error.register;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.joboffers.infrastructure.loginandregister.controller.TokenRestController;

@ControllerAdvice(basePackageClasses = TokenRestController.class)
public class RegisterControllerErrorHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public RegisterErrorResponse handleDuplicateKeyException() {
        final String message = "User with given username already exists";
        return new RegisterErrorResponse(message, HttpStatus.CONFLICT);
    }

}
