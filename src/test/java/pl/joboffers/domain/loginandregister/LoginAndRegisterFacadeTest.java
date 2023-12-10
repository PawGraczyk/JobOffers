package pl.joboffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.BadCredentialsException;
import pl.joboffers.domain.loginandregister.dto.FindByUsernameRequestDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;


class LoginAndRegisterFacadeTest {
    private final LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new UserRepositoryTestImplementation());

    @Test
    public void should_return_user_by_given_username() {
        //given
        RegistrationUserDto registration = new RegistrationUserDto("Username", "qwerty");
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registration);
        FindByUsernameRequestDto requestDto = new FindByUsernameRequestDto(registrationResultDto.getUsername());
        //when
        UserDto result = loginAndRegisterFacade.findUserByUsername(requestDto);
        //then
        assertThat(result).isEqualTo(new UserDto(result.getId(), result.getUsername(), result.getPassword()));

    }

    @Test
    public void should_throw_exception_when_no_user_found() {
        //given
        FindByUsernameRequestDto requestDto = new FindByUsernameRequestDto("NotExistingUser");
        //when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.findUserByUsername(requestDto));
        //then
        assertAll(() -> assertThat(throwable).isInstanceOf(BadCredentialsException.class), () -> assertThat(throwable.getMessage()).isEqualTo("User not found."));
    }

    @Test
    public void should_return_successful_result_when_registering_new_user() {
        //given
        RegistrationUserDto registration = new RegistrationUserDto("Username", "qwerty");
        //when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(registration);
        //then
        assertAll(() -> assertThat(registrationResultDto.getUsername()).isEqualTo(registration.getUsername()), () -> assertThat(registrationResultDto.isRegistered()).isEqualTo(true));
    }

    @Test
    public void should_throw_DuplicateKeyException_when_registering_existing_username() {
        //given
        RegistrationUserDto registration = new RegistrationUserDto("Username", "qwerty");
        loginAndRegisterFacade.register(registration);
        //when
        Throwable throwable = catchThrowable(() -> loginAndRegisterFacade.register(registration));
        // then
        assertAll(() -> assertThat(throwable).isInstanceOf(DuplicateKeyException.class));
    }

}