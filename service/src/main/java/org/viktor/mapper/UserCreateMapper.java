package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.UserCreateDto;
import org.viktor.entity.UserEntity;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, UserEntity> {

    @Override
    public UserEntity map(UserCreateDto fromObject, UserEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public UserEntity map(UserCreateDto object) {
        var user = new UserEntity();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateDto object, UserEntity user) {
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
    }
}