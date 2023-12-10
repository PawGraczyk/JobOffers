package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.loginandregister.dto.FindByUsernameRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {

    private final static String USER_NOT_FOUND_MESSAGE = "User not found.";
    private final UserRepository repository;

    public UserDto findUserByUsername(FindByUsernameRequestDto inputUsernameRequest) {
        return repository.findUserByUsername(inputUsernameRequest.getUsername()).map(UserMapper::mapFromUserToUserDto).orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND_MESSAGE));
    }

    public RegistrationResultDto register(RegistrationUserDto registrationDto) {
        User user = UserMapper.mapFromRegistrationUserDtoToUser(registrationDto);
        User registeredUser = repository.save(user);
        return new RegistrationResultDto(registeredUser.getId(), registeredUser.getUsername(), true);
    }
}
