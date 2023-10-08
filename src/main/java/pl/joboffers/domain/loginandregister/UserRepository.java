package pl.joboffers.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String inputUsername);

    User register(User user);
}
