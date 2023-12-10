package pl.joboffers.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import pl.joboffers.domain.loginandregister.dto.FindByUsernameRequestDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        FindByUsernameRequestDto findByUsernameRequestDto = new FindByUsernameRequestDto(username);
        UserDto userDto = loginAndRegisterFacade.findUserByUsername(findByUsernameRequestDto);
        return getUser(userDto);
    }

    private org.springframework.security.core.userdetails.User getUser(UserDto userDto) {
        return new org.springframework.security.core.userdetails.User(userDto.getUsername(), userDto.getPassword(), Collections.emptyList());
    }
}
