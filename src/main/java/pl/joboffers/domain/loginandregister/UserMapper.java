package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.RegistrationUserDto;
import pl.joboffers.domain.loginandregister.dto.UserDto;

class UserMapper {

    public static UserDto mapFromUserToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword());
    }

    public static User mapFromRegistrationUserDtoToUser(RegistrationUserDto registrationDto) {
        return new User(registrationDto.getUsername(), registrationDto.getPassword());
    }
}
