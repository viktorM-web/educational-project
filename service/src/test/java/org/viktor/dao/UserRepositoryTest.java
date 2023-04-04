package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends RepositoryTestBase {

    private final UserRepository userRepository = context.getBean(UserRepository.class);

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
        entityManager.clear();

        assertThat(userRepository.findById(expectedUser.getId())).isEmpty();
    }

    @Test
    void update() {
        UserEntity expectedUser = userRepository.findById(1).get();

        expectedUser.setEmail("ivan@gmail.com");
        userRepository.update(expectedUser);
        entityManager.clear();

        assertThat(userRepository.findById(expectedUser.getId()).get()).isEqualTo(expectedUser);
    }

    @Test
    void findById() {
        UserEntity expectedUser = userRepository.findById(1).get();
        entityManager.clear();

        assertThat(expectedUser.getEmail()).isEqualTo("ivan@mail.ru");
    }

    @Test
    void findAll() {
        List<UserEntity> expectedUser = userRepository.findAll();
        entityManager.clear();

        assertThat(expectedUser).hasSize(2);
    }
}
