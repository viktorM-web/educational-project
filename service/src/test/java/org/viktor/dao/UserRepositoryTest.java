package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends RepositoryTestBase {

    private final UserRepository userRepository = new UserRepository(session);

    @Test
    void save() {
        UserEntity user = EntityUtil.buildUser();

        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void delete() {
        UserEntity expectedUser = userRepository.findById(2).get();

        userRepository.delete(expectedUser);
        session.clear();

        assertThat(userRepository.findById(expectedUser.getId())).isEmpty();
    }

    @Test
    void update() {
        UserEntity expectedUser = userRepository.findById(1).get();

        expectedUser.setEmail("ivan@gmail.com");
        userRepository.update(expectedUser);
        session.clear();

        assertThat(userRepository.findById(expectedUser.getId()).get()).isEqualTo(expectedUser);
    }

    @Test
    void findById() {
        UserEntity expectedUser = userRepository.findById(1).get();
        session.clear();

        assertThat(expectedUser.getEmail()).isEqualTo("ivan@mail.ru");
    }

    @Test
    void findAll() {
        List<UserEntity> expectedUser = userRepository.findAll();
        session.clear();

        assertThat(expectedUser).hasSize(2);
    }
}
