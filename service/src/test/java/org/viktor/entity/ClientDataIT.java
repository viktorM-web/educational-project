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

public class ClientDataIT {
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
    void saveClientData() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        ClientDataEntity clientData = EntityUtil.buildClientData(user);

        session.save(clientData);

        assertThat(clientData.getId()).isNotNull();
    }

    @Test
    void getClientData() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isEqualTo(expectedClientData);
    }

    @Test
    void update() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.clear();
        expectedClientData.setDriverLicenceNo("12354ad");
        session.update(expectedClientData);
        session.flush();
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isEqualTo(expectedClientData);
    }

    @Test
    void delete() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        ClientDataEntity expectedClientData = EntityUtil.buildClientData(user);
        session.save(expectedClientData);
        session.clear();
        session.delete(expectedClientData);
        session.flush();
        session.clear();

        ClientDataEntity actualClientData = session.get(ClientDataEntity.class, expectedClientData.getId());

        assertThat(actualClientData).isNull();
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
}
