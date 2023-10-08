package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RegistrationTextMessageType {
    SUCCESS("Successful registration!"),
    FAILURE("Failure. User with given username already exists.");

    private final String description;

}