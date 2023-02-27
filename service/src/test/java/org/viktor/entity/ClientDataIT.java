package org.viktor.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.util.HibernateTestUtil;

import java.math.BigDecimal;

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
    void saveCarCategory() {
        CarCategoryEntity carCategory = buildCarCategory();

        session.save(carCategory);

        assertThat(carCategory.getId()).isNotNull();
    }

    @Test
    void getCarCategory() {
        CarCategoryEntity expectedCarCategory = buildCarCategory();
        session.save(expectedCarCategory);
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isEqualTo(expectedCarCategory);
    }

    @Test
    void update() {
        CarCategoryEntity expectedCarCategory = buildCarCategory();
        session.save(expectedCarCategory);
        expectedCarCategory.setCategory("E");
        session.update(expectedCarCategory);
        session.flush();
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isEqualTo(expectedCarCategory);
    }

    @Test
    void delete() {
        CarCategoryEntity expectedCarCategory = buildCarCategory();
        session.save(expectedCarCategory);
        session.clear();
        session.delete(expectedCarCategory);
        session.flush();
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isNull();
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

    private static CarCategoryEntity buildCarCategory() {
        return CarCategoryEntity.builder()
                .category("S")
                .dayPrice(BigDecimal.valueOf(60.12))
                .build();
    }
}
