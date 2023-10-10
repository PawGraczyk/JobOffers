package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.UserRequestDto;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class UserRepositoryTestImplementation implements UserRepository {

    private final static String USER_ALREADY_EXISTS_MESSAGE = "User already exists";

    private final Map<String, User> inMemoryUserDatabase = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByUsername(UserRequestDto requestByUsername) {
        return Optional.ofNullable(inMemoryUserDatabase.get(requestByUsername.username()));
    }

    @Override
    public User register(User user) {
        if (inMemoryUserDatabase.containsKey(user.username())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE);
        } else {
            inMemoryUserDatabase.put(user.username(), user);
            return user;
        }
    }
}
