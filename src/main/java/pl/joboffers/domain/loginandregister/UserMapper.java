package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.RegistrationDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

class UserMapper {

    public static UserDto mapFromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .build();
    }

    public static User mapFromRegistrationUserDtoToUser(RegistrationDto registrationDto) {
        return User.builder()
                .username(registrationDto.username())
                .password(registrationDto.password())
                .build();
    }
}
