package org.viktor.spring.unit.service;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.viktor.dto.UserCreateDto;
import org.viktor.dto.UserFilter;
import org.viktor.dto.UserReadDto;
import org.viktor.entity.Role;
import org.viktor.entity.UserEntity;
import org.viktor.mapper.UserCreateMapper;
import org.viktor.mapper.UserReadMapper;
import org.viktor.repository.QPredicate;
import org.viktor.repository.UserRepository;
import org.viktor.security.UserSecurity;
import org.viktor.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.viktor.entity.QUserEntity.userEntity;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserReadMapper userReadMapper;

    @Mock
    private UserCreateMapper userCreateMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
        var users = List.of(
                new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null),
                new UserEntity(2, "test2@goodle.com", "111", Role.CLIENT, null));

        var expectedResponse = List.of(
                new UserReadDto(1, "test1@goodle.com", Role.ADMIN),
                new UserReadDto(2, "test2@goodle.com", Role.CLIENT));

        doReturn(users).when(userRepository).findAll();
        doReturn(new UserReadDto(1, "test1@goodle.com", Role.ADMIN),
                new UserReadDto(2, "test2@goodle.com", Role.CLIENT))
                .when(userReadMapper).map(any(UserEntity.class));

        var actualResponse = userService.findAll();

        assertThat(actualResponse).hasSize(2);
        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void testFindAll() {
        var pageable = PageRequest.of(0, 2);
        var filter = new UserFilter("Te", Role.CLIENT);
        var predicate = buildPredicate(filter);

        Page<UserEntity> users = new PageImpl<>(List.of(
                new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null),
                new UserEntity(2, "test2@goodle.com", "111", Role.CLIENT, null)));

        Page<UserReadDto> expectedResponse = new PageImpl<>(List.of(
                new UserReadDto(1, "test1@goodle.com", Role.ADMIN),
                new UserReadDto(2, "test2@goodle.com", Role.CLIENT)));

        doReturn(users).when(userRepository).findAll(predicate, pageable);

        doReturn(new UserReadDto(1, "test1@goodle.com", Role.ADMIN),
                new UserReadDto(2, "test2@goodle.com", Role.CLIENT))
                .when(userReadMapper).map(any(UserEntity.class));

        var actualResponse = userService.findAll(filter, pageable);

        assertThat(actualResponse).hasSize(2);
        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void findById() {
        var userEntity = Optional.of(
                new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null));
        var expectedResponse = new UserReadDto(1, "test1@goodle.com", Role.ADMIN);
        doReturn(userEntity).when(userRepository).findById(1);
        doReturn(expectedResponse).when(userReadMapper).map(any(UserEntity.class));

        var actualResponse = userService.findById(1);

        assertThat(Optional.of(expectedResponse)).isEqualTo(actualResponse);
    }

    @Test
    void create() {
        var userCreateDto = new UserCreateDto("test1@goodle.com", "111", Role.ADMIN);
        var user = new UserEntity(null, "test1@goodle.com", "111", Role.ADMIN, null);
        var userAfterSave = new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null);
        var expectedResponse = new UserReadDto(1, "test1@goodle.com", Role.ADMIN);
        doReturn(user).when(userCreateMapper).map(userCreateDto);
        doReturn(userAfterSave).when(userRepository).save(user);
        doReturn(expectedResponse).when(userReadMapper).map(userAfterSave);

        var actualResponse = userService.create(userCreateDto);

        assertThat(actualResponse.getId()).isNotNull();
        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void update() {
        var user = Optional.of(new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null));
        var userUpdateDto = new UserCreateDto("test2@goodle.com", "111", Role.CLIENT);
        var updatedUser = new UserEntity(1, "test2@goodle.com", "111", Role.CLIENT, null);
        var expectedResponse = new UserReadDto(1, "test2@goodle.com", Role.CLIENT);
        doReturn(user).when(userRepository).findById(1);
        doReturn(updatedUser).when(userCreateMapper).map(userUpdateDto, user.get());
        doReturn(updatedUser).when(userRepository).saveAndFlush(updatedUser);
        doReturn(expectedResponse).when(userReadMapper).map(updatedUser);

        var actualResponse = userService.update(1, userUpdateDto);

        assertThat(Optional.of(expectedResponse)).isEqualTo(actualResponse);
    }

    @Test
    void delete() {
        var user = new UserEntity(1, "test1@goodle.com", "111", Role.ADMIN, null);
        doReturn(Optional.of(user)).when(userRepository).findById(1);

        var expectedResult = userService.delete(1);

        assertTrue(expectedResult);
    }

    @Test
    void loadUserByUsername(){
        var expectedUserSecurity =
                new UserSecurity("ivan@mail.ru", "{noop}123", Set.of(Role.ADMIN), 1);
        var userEntity = new UserEntity(1, "ivan@mail.ru", "{noop}123", Role.ADMIN, null);
        doReturn(Optional.of(userEntity)).when(userRepository).findByEmail("ivan@mail.ru");

        var actualUserSecurity = userService.loadUserByUsername("ivan@mail.ru");

        assertThat(expectedUserSecurity).isEqualTo(actualUserSecurity);
    }

    private Predicate buildPredicate(UserFilter filter) {
        return QPredicate.builder()
                .add(filter.getEmail(), userEntity.email::containsIgnoreCase)
                .add(filter.getRole(), userEntity.role::eq)
                .buildAnd();
    }
}
