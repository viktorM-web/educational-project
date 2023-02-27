package org.viktor.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.util.HibernateTestUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderIT {

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
    void saveOrder() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity order = EntityUtil.buildOrder(user, car);

        session.save(order);

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void getOrder() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.clear();

        OrderEntity actualOrder = session.get(OrderEntity.class, expectedOrder.getId());

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    void update() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.clear();
        expectedOrder.setStartDateUse(LocalDateTime.of(2023, 3, 1, 2, 0));
        session.update(expectedOrder);
        session.flush();
        session.clear();

        OrderEntity actualOrder = session.get(OrderEntity.class, expectedOrder.getId());

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    void delete() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.clear();
        session.delete(expectedOrder);
        session.flush();
        session.clear();

        OrderEntity actualOrder = session.get(OrderEntity.class, expectedOrder.getId());

        assertThat(actualOrder).isNull();
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
