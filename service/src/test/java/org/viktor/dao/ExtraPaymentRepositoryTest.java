package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.ExtraPaymentEntity;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtraPaymentRepositoryTest extends RepositoryTestBase {

    private final CarRepository carRepository = context.getBean(CarRepository.class);
    private final CarCategoryRepository carCategoryRepository = context.getBean(CarCategoryRepository.class);
    private final UserRepository userRepository = context.getBean(UserRepository.class);
    private final OrderRepository orderRepository = context.getBean(OrderRepository.class);
    private final ExtraPaymentRepository extraPaymentRepository = context.getBean(ExtraPaymentRepository.class);

    @Test
    void save() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        carCategoryRepository.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        carRepository.save(car);
        UserEntity user = EntityUtil.buildUser();
        userRepository.save(user);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        orderRepository.save(order);
        ExtraPaymentEntity extraPayment = EntityUtil.buildPayment(order);

        extraPaymentRepository.save(extraPayment);

        assertThat(extraPayment.getId()).isNotNull();
    }

    @Test
    void delete() {
        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(1).get();

        extraPaymentRepository.delete(expectedExtraPayment);
        entityManager.clear();

        assertThat(extraPaymentRepository.findById(expectedExtraPayment.getId())).isEmpty();
    }

    @Test
    void update() {
        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(1).get();

        expectedExtraPayment.setDescription("speeding fine 2023.1.4 14:00 99.99$");
        extraPaymentRepository.update(expectedExtraPayment);
        entityManager.clear();

        assertThat(extraPaymentRepository.findById(expectedExtraPayment.getId()).get()).isEqualTo(expectedExtraPayment);
    }

    @Test
    void findById() {
        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(1).get();
        entityManager.clear();

        assertThat(expectedExtraPayment.getDescription()).isEqualTo("speeding fine 2023.1.4 14:00 50.00$");
    }

    @Test
    void findAll() {
        List<ExtraPaymentEntity> expectedExtraPayment = extraPaymentRepository.findAll();
        entityManager.clear();

        assertThat(expectedExtraPayment).hasSize(1);
    }
}
