package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.UserReadDto;
import org.viktor.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<UserEntity, UserReadDto> {

    @Override
    public UserReadDto map(UserEntity object) {
        return new UserReadDto(
                object.getId(),
                object.getEmail(),
                object.getRole()
        );
    }
}

