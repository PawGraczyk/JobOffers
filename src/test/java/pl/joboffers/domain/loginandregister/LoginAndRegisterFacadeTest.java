package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import pl.joboffers.domain.loginandregister.dto.UserRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LoginAndRegisterFacadeTest {
    private final LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImplementation()
    );

    @Test
    public void should_return_user_by_given_username() {
        //given
        RegistrationUserDto registrationUser = RegistrationUserDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.registerNewUser(registrationUser);
        UserRequestDto requestDto = UserRequestDto.builder()
                .username(registrationResultDto.username())
                .build();
        //when
        UserDto result = loginAndRegisterFacade.findUserByUsername(requestDto);
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
        UserRequestDto requestDto = UserRequestDto.builder()
                .username("NotExistingUser")
                .build();
        //when
        //then
        assertThrows(UserNotFoundException.class,
                () -> loginAndRegisterFacade.findUserByUsername(requestDto),
                "User not found.");
    }

    @Test
    public void should_return_successful_result_when_registering_new_user() {
        //given
        RegistrationUserDto registrationUser = RegistrationUserDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.registerNewUser(registrationUser);
        //then
        assertAll(
                () -> assertThat(registrationResultDto.username()).isEqualTo(registrationUser.username()),
                () -> assertThat(registrationResultDto.isRegistered()).isEqualTo(true)
        );
    }

    @Test
    public void should_return_negative_result_when_registering_existing_username() {
        //given
        RegistrationUserDto registrationUserDto = RegistrationUserDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        loginAndRegisterFacade.registerNewUser(registrationUserDto);
        //when
        RegistrationResultDto secondRegistration = loginAndRegisterFacade.registerNewUser(registrationUserDto);
        //then
        assertAll(
                () -> assertThat(secondRegistration.username()).isEqualTo(registrationUserDto.username()),
                () -> assertThat(secondRegistration.isRegistered()).isEqualTo(false));

    }
}