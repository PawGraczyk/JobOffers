package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.dto.UserDto;

class UserMapper {

    public static UserDto mapFromUser(User user) {
        return UserDto.builder()
                .username(user.username())
                .password(user.password())
                .build();
    }

}
