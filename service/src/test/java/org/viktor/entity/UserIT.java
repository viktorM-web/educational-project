package org.viktor.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.util.HibernateTestUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIT {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void initSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    void save() {
        UserEntity user = buildUser();

        session.save(user);

        assertThat(user.getId()).isNotNull();
    }


    @Test
    void get() {
        UserEntity expectedUser = buildUser();
        session.save(expectedUser);
        session.clear();

        UserEntity actualUser = session.get(UserEntity.class, expectedUser.getId());

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    void update() {
        UserEntity expectedUser = buildUser();
        session.save(expectedUser);
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
        session.delete(expectedUser);
        session.flush();
        session.clear();

        UserEntity actualUser = session.get(UserEntity.class, expectedUser.getId());

        assertThat(actualUser).isNull();
    }


    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    private static UserEntity buildUser() {
        return UserEntity.builder()
                .email("ivan@mail.ru")
                .password("123")
                .role(Role.CLIENT)
                .build();
    }
}
