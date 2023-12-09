package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import pl.joboffers.domain.loginandregister.dto.FindByUsernameRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;


class LoginAndRegisterFacadeTest {
    private final LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new UserRepositoryTestImplementation()
    );

    @Test
    public void should_return_user_by_given_username() {
        //given
        RegistrationDto registrationUser = RegistrationDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registrationUser);
        FindByUsernameRequestDto requestDto = FindByUsernameRequestDto.builder()
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
        FindByUsernameRequestDto requestDto = FindByUsernameRequestDto.builder()
                .username("NotExistingUser")
                .build();
        //when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.findUserByUsername(requestDto));
        //then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(BadCredentialsException.class),
                () -> assertThat(throwable.getMessage()).isEqualTo("User not found.")
        );
    }

    @Test
    public void should_return_successful_result_when_registering_new_user() {
        //given
        RegistrationDto registrationUser = RegistrationDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registrationUser);
        //then
        assertAll(
                () -> assertThat(registrationResultDto.username()).isEqualTo(registrationUser.username()),
                () -> assertThat(registrationResultDto.isRegistered()).isEqualTo(true)
        );
    }

    @Test
    public void should_return_negative_result_when_registering_existing_username() {
        //given
        RegistrationDto registrationDto = RegistrationDto.builder()
                .username("Username")
                .password("qwerty")
                .build();
        loginAndRegisterFacade.register(registrationDto);
        //when
        RegistrationResultDto secondRegistration = loginAndRegisterFacade.register(registrationDto);
        //then
        assertAll(
                () -> assertThat(secondRegistration.username()).isEqualTo(registrationDto.username()),
                () -> assertThat(secondRegistration.isRegistered()).isEqualTo(false));

    }
}