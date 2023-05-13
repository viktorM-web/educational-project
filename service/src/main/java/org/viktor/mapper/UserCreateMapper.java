package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.viktor.dto.UserCreateDto;
import org.viktor.entity.UserEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, UserEntity> {

    private final PasswordEncoder passwordEncoder;

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
        user.setRole(object.getRole());

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
    }
}