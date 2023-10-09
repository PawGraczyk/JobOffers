package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.FindByUsernameRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationMessageDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {


    private final static String USER_NOT_FOUND_MESSAGE = "User not found.";
    private final UserRepository repository;

    public UserDto findUserByUsername(FindByUsernameRequestDto inputUsernameRequest) {
        return repository.findByUsername(inputUsernameRequest.username())
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
    }

    public RegistrationMessageDto registerNewUser(RegistrationUserDto registrationUserDto) {
        User user = UserMapper.mapFromRegistrationUserDtoToUser(registrationUserDto);
        User registeredUser = repository.register(user);
        return RegistrationMessageDto.builder()
                .id(registeredUser.id())
                .username(registeredUser.username())
                .isRegistered(true)
                .build();

    }

}
