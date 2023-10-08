package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.RegistrationMessageDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class LoginAndRegisterFacadeTest {
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImplementation()
    );

    @Test
    public void should_return_user_by_given_username() {
        //given
        loginAndRegisterFacade.registerNewUser("AnotherUser", "qwerty");
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
    public void should_throw_exception_when_no_user_found() {
        //given
        String inputUsername = "NotExistingUser";
        //when
        //then
        assertThrows(UserNotFoundException.class, () -> loginAndRegisterFacade.findUserByUsername(inputUsername), "No user with username " + inputUsername + " found");
    }

    @Test
    public void should_return_failure_message_when_saving_already_existing_user() {
        //given
        String inputUsername = "ExistingUser";
        String inputPassword = "12345";
        loginAndRegisterFacade.registerNewUser(inputUsername, inputPassword);
        //when
        RegistrationMessageDto registrationMessageDto = loginAndRegisterFacade.registerNewUser("ExistingUser", "qwerty");
        //then
        assertThat(registrationMessageDto).isEqualTo(RegistrationMessageDto
                .builder()
                .message(RegistrationTextMessageType.FAILURE)
                .build()
        );
    }
    @Test
    public void should_return_success_message_when_saving_new_user(){
        //given
        String inputUsername = "ExistingUser";
        String inputPassword = "12345";
        //when
        RegistrationMessageDto registrationMessageDto = loginAndRegisterFacade.registerNewUser(inputUsername, inputPassword);
        //then
        assertThat(registrationMessageDto).isEqualTo(RegistrationMessageDto
                .builder()
                        .username(inputUsername)
                        .password(inputPassword)
                        .message(RegistrationTextMessageType.SUCCESS)
                .build()
        );


    }

}