package org.viktor.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ExtraPaymentIT extends EntityTestBase {

    @Test
    void saveExtraPayment() {
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
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);

        session.save(expectedPayment);
        session.flush();

        assertThat(expectedPayment.getId()).isNotNull();
    }

    @Test
    void getExtraPayment() {
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
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);
        session.save(expectedPayment);
        session.flush();
        session.clear();

        ExtraPaymentEntity actualPayment = session.get(ExtraPaymentEntity.class, expectedPayment.getId());

        assertThat(actualPayment).isEqualTo(expectedPayment);
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
        OrderEntity order = EntityUtil.buildOrder(user, car);
        session.save(order);
        session.flush();
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);
        session.save(expectedPayment);
        session.flush();
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
        ExtraPaymentEntity expectedPayment = EntityUtil.buildPayment(order);
        session.save(expectedPayment);
        session.flush();
        session.clear();

        session.delete(expectedPayment);
        session.flush();
        session.clear();

        ExtraPaymentEntity actualPayment = session.get(ExtraPaymentEntity.class, expectedPayment.getId());

        assertThat(actualPayment).isNull();
    }
}
