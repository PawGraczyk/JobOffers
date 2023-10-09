package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

class UserMapper {

    public static UserDto mapFromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .build();
    }
    public static User mapFromRegistrationUserDtoToUser(RegistrationUserDto registrationUserDto){
        return User.builder()
                .username(registrationUserDto.username())
                .password(registrationUserDto.password())
                .build();
    }
}
