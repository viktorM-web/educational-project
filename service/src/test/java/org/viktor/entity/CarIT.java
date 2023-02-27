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

public class CarIT {

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
    void saveCar() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);

        session.save(car);

        assertThat(car.getId()).isNotNull();
    }

    @Test
    void getCar() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.clear();

        CarEntity actualCar = session.get(CarEntity.class, expectedCar.getId());

        assertThat(actualCar).isEqualTo(expectedCar);
    }

    @Test
    void update() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.clear();
        expectedCar.setVinCode("12545454er154trerg");
        session.update(expectedCar);
        session.flush();
        session.clear();

        CarEntity actualCar = session.get(CarEntity.class, expectedCar.getId());

        assertThat(actualCar).isEqualTo(expectedCar);
    }

    @Test
    void delete() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.clear();
        session.delete(expectedCar);
        session.flush();
        session.clear();

        CarEntity actualCar = session.get(CarEntity.class, expectedCar.getId());

        assertThat(actualCar).isNull();
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
