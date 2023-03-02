package org.viktor.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderIT extends EntityTestBase {

    @Test
    void saveOrder() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        session.flush();
        OrderEntity order = EntityUtil.buildOrder(user, car);

        session.save(order);
        session.flush();

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void getOrder() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        session.flush();
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.flush();
        session.clear();

        OrderEntity actualOrder = session.get(OrderEntity.class, expectedOrder.getId());

        assertThat(actualOrder).isEqualTo(expectedOrder);
    }

    @Test
    void update() {
        UserEntity user = EntityUtil.buildUser();
        session.save(user);
        session.flush();
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        session.flush();
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.flush();
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
        session.flush();
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        session.save(carCategory);
        session.flush();
        CarEntity car = EntityUtil.buildCar(carCategory);
        session.save(car);
        session.flush();
        OrderEntity expectedOrder = EntityUtil.buildOrder(user, car);
        session.save(expectedOrder);
        session.flush();
        session.clear();
        session.delete(expectedOrder);
        session.flush();
        session.clear();

        OrderEntity actualOrder = session.get(OrderEntity.class, expectedOrder.getId());

        assertThat(actualOrder).isNull();
    }
}
