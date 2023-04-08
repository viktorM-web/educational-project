package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.dao.UserRepository;
import org.viktor.util.EntityUtil;
import org.viktor.entity.UserEntity;
import org.viktor.spring.integration.annotation.IT;
import org.viktor.util.TestDataImporter;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initData() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void save() {
        UserEntity user = saveUser();

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void delete() {
        UserEntity user = saveUser();
        entityManager.clear();

        UserEntity expectedUser = userRepository.findById(user.getId()).get();

        userRepository.delete(expectedUser);
        entityManager.clear();

        assertThat(userRepository.findById(expectedUser.getId())).isEmpty();
    }

    @Test
    void update() {
        UserEntity user = saveUser();
        entityManager.clear();

        UserEntity expectedUser = userRepository.findById(user.getId()).get();

        expectedUser.setEmail("XXXX@gmail.com");
        userRepository.update(expectedUser);
        entityManager.clear();

        assertThat(userRepository.findById(expectedUser.getId()).get()).isEqualTo(expectedUser);
    }

    @Test
    void findById() {
        UserEntity user = saveUser();
        entityManager.clear();

        UserEntity expectedUser = userRepository.findById(user.getId()).get();
        entityManager.clear();

        assertThat(expectedUser.getEmail()).isEqualTo("TEST@mail.ru");
    }

    @Test
    void findAll() {
        List<UserEntity> expectedUser = userRepository.findAll();
        entityManager.clear();

        assertThat(expectedUser).hasSize(2);
    }

    private UserEntity saveUser() {
        UserEntity user = EntityUtil.buildUser();
        userRepository.save(user);
        return user;
    }
}
