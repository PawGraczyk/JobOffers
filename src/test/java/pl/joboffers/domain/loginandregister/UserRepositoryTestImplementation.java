package pl.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class UserRepositoryTestImplementation implements UserRepository {

    private final static String USER_ALREADY_EXISTS_MESSAGE = "User already exists";

    private final Map<String, User> inMemoryUserDatabase = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByUsername(String inputUsername) {
        return Optional.ofNullable(inMemoryUserDatabase.get(inputUsername));
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
