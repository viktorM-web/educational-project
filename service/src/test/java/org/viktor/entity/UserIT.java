package org.viktor.entity;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.viktor.entity.EntityUtil.buildUser;

public class UserIT extends EntityTestBase {

    @Test
    void save() {
        UserEntity user = buildUser();

        session.save(user);
        session.flush();

        assertThat(user.getId()).isNotNull();
    }

    @Test
    void get() {
        UserEntity expectedUser = buildUser();
        session.save(expectedUser);
        session.flush();
        session.clear();

        UserEntity actualUser = session.get(UserEntity.class, expectedUser.getId());

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void update() {
        UserEntity expectedUser = buildUser();
        session.save(expectedUser);
        session.flush();
        expectedUser.setEmail("newIvan@Mail.ru");
        session.update(expectedUser);
        session.flush();
        session.clear();

        UserEntity actualUser = session.get(UserEntity.class, expectedUser.getId());

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void delete() {
        UserEntity expectedUser = buildUser();
        session.save(expectedUser);
        session.flush();
        session.clear();
        session.delete(expectedUser);
        session.flush();
        session.clear();

        UserEntity actualUser = session.get(UserEntity.class, expectedUser.getId());

        assertThat(actualUser).isNull();
    }
}
