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

public class ExtraPaymentIT {

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
    void saveExtraPayment() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        session.save(order);
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);

        session.save(expectedPayment);

        assertThat(expectedPayment.getId()).isNotNull();
    }

    @Test
    void getExtraPayment() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        session.save(order);
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);

        session.save(expectedPayment);
        session.clear();

        ExtraPaymentEntity actualPayment = session.get(ExtraPaymentEntity.class, expectedPayment.getId());

        assertThat(actualPayment).isEqualTo(expectedPayment);
    }

    @Test
    void update() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        session.save(order);
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);
        session.save(expectedPayment);
        session.clear();
        expectedPayment.setDescription("speeding fine 2023.3.2 14:00 60.00$");
        expectedPayment.setPrice(BigDecimal.valueOf(60.0).setScale(2));
        session.update(expectedPayment);
        session.flush();
        session.clear();

        ExtraPaymentEntity actualPayment = session.get(ExtraPaymentEntity.class, expectedPayment.getId());

        assertThat(actualPayment).isEqualTo(expectedPayment);
    }

    @Test
    void delete() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        session.save(order);
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);
        session.save(expectedPayment);
        session.clear();
        session.delete(expectedPayment);
        session.flush();
        session.clear();

        ExtraPaymentEntity actualPayment = session.get(ExtraPaymentEntity.class, expectedPayment.getId());

        assertThat(actualPayment).isNull();
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
