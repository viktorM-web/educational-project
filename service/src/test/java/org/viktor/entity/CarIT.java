package org.viktor.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarIT extends EntityTestBase {

    @Test
    void saveCar() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity car = EntityUtil.buildCar(carCategory);

        session.save(car);
        session.flush();

        assertThat(car.getId()).isNotNull();
    }

    @Test
    void getCar() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.flush();
        session.clear();

        CarEntity actualCar = session.get(CarEntity.class, expectedCar.getId());

        assertThat(actualCar).isEqualTo(expectedCar);
    }

    @Test
    void update() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.flush();
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
        session.flush();
        CarEntity expectedCar = EntityUtil.buildCar(carCategory);
        session.save(expectedCar);
        session.flush();
        session.clear();

        session.delete(expectedCar);
        session.flush();
        session.clear();

        CarEntity actualCar = session.get(CarEntity.class, expectedCar.getId());

        assertThat(actualCar).isNull();
    }

}
