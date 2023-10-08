package pl.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;


public class UserRepositoryTestImplementation implements UserRepository{

    private Map<String, User> inMemoryUsersDatabase = Map.of(
            ("TestUsername"), new User("TestUsername", "12345"),
            ("AnotherUser"), new User("AnotherUser", "qwerty")
            );

    @Override
    public User save(User user) {
        //TO DO
        return null;
    }

    @Override
    public Optional<User> findUserByUsername(String inputUsername) {
        return Optional.ofNullable(inMemoryUsersDatabase.get(inputUsername));
    }
}
