package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.dto.UserDto;


@AllArgsConstructor
public class LoginAndRegisterFacade {

    private final UserRepository repository;

    public UserDto findUserByUsername(String inputUsername) {
        return repository.findUserByUsername(inputUsername)
                .map(UserMapper::mapFromUser)
                .orElseThrow(() -> new UserNotFoundException("No user with username " + inputUsername + " found"));
    }
}
