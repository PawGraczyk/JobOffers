package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.UserRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final static String USER_NOT_FOUND_MESSAGE = "User not found.";
    private final UserRepository repository;

    public UserDto findUserByUsername(UserRequestDto requestByUsername) {
        return repository.findByUsername(requestByUsername)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public RegistrationResultDto registerNewUser(RegistrationUserDto registrationUserDto) {
        try {
            User user = UserMapper.mapFromRegistrationUserDtoToUser(registrationUserDto);
            User registeredUser = repository.register(user);
            return RegistrationResultDto.builder()
                    .id(registeredUser.id())
                    .username(registeredUser.username())
                    .isRegistered(true)
                    .build();
        } catch (UserAlreadyExistsException ex) {
            return RegistrationResultDto.builder()
                    .username(registrationUserDto.username())
                    .isRegistered(false)
                    .build();
        }
    }
}
