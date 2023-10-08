package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.RegistrationMessageDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository repository;

    public UserDto findUserByUsername(String inputUsername) {
        return repository.findByUsername(inputUsername)
                .map(UserMapper::mapFromUser)
                .orElseThrow(() -> new UserNotFoundException("No user with username " + inputUsername + " found"));
    }

    public RegistrationMessageDto registerNewUser(String inputUsername, String inputPassword){
        try {
            findUserByUsername(inputUsername);
            return RegistrationMessageDto.builder()
                    .message(RegistrationTextMessageType.FAILURE)
                    .build();
        } catch (UserNotFoundException ex){
            User newUser = repository.register(new User(inputUsername, inputPassword));
            return RegistrationMessageDto.builder()
                    .username(newUser.username())
                    .password(newUser.password())
                    .message(RegistrationTextMessageType.SUCCESS)
                    .build();

        }
    }

}
