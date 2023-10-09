package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.RegistrationMessageDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository repository;

    public UserDto findUserByUsername(String inputUsername) {
        return repository.findByUsername(inputUsername)
                .map(UserMapper::mapFromUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("No user with username " + inputUsername + " found"));
    }

    public RegistrationMessageDto registerNewUser(RegistrationUserDto registrationUserDto) {
        User user = UserMapper.mapFromRegistrationUserDtoToUser(registrationUserDto);
        User registeredUser = repository.register(user);
        return RegistrationMessageDto.builder()
                .id(registeredUser.id())
                .username(registeredUser.username())
                .message(RegistrationTextMessageType.SUCCESS)
                .build();

    }

}
