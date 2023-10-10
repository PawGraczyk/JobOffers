package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.UserRequestDto;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(UserRequestDto requestByUsername);

    User register(User user);
}
