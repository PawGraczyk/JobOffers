package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LoginAndRegisterFacadeTest {
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImplementation()
    );

    @Test
    public void should_return_user_dto_by_given_username() {
        //given
        String inputUsername = "AnotherUser";
        //when
        UserDto result = loginAndRegisterFacade.findUserByUsername(inputUsername);
        //then
        assertThat(result).isEqualTo(UserDto.builder()
                .username(result.username())
                .password(result.password())
                .build());

    }

    @Test
    public void should_return_exception_when_no_user_found() {
        //given
        String inputUsername = "NotExistingUser";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> loginAndRegisterFacade.findUserByUsername(inputUsername), "No user with username " + inputUsername + " found");

    }
}