package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.RegistrationMessageDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LoginAndRegisterFacadeTest {
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImplementation()
    );

    @Test
    public void should_return_user_by_given_username() {
        //given
        RegistrationUserDto registrationUser = RegistrationUserDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        loginAndRegisterFacade.registerNewUser(registrationUser);
        //when
        UserDto result = loginAndRegisterFacade.findUserByUsername(registrationUser.username());
        //then
        assertThat(result).isEqualTo(UserDto.builder()
                .id(result.id())
                .username(result.username())
                .password(result.password())
                .build());
    }
    @Test
    public void should_throw_exception_when_no_user_found() {
        //given
        String inputUsername = "NotExistingUser";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> loginAndRegisterFacade.findUserByUsername(inputUsername), "No user with username " + inputUsername + " found");
    }

    @Test
    public void should_return_success_message_when_saving_new_user() {
        //given
        RegistrationUserDto registrationUser = RegistrationUserDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        //when
        RegistrationMessageDto registrationMessageDto = loginAndRegisterFacade.registerNewUser(registrationUser);
        //then
        assertAll(
                () -> assertThat(registrationMessageDto.username()).isEqualTo(registrationUser.username()),
                () -> assertThat(registrationMessageDto.message()).isEqualTo(RegistrationTextMessageType.SUCCESS)
        );
    }
}