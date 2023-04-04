package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.entity.EntityUtil;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.Status;
import org.viktor.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest extends RepositoryTestBase {

    private final CarRepository carRepository = context.getBean(CarRepository.class);
    private final CarCategoryRepository carCategoryRepository = context.getBean(CarCategoryRepository.class);
    private final UserRepository userRepository = context.getBean(UserRepository.class);
    private final OrderRepository orderRepository = context.getBean(OrderRepository.class);

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

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void delete() {
        OrderEntity expectedOrder = orderRepository.findById(1).get();

        orderRepository.delete(expectedOrder);
        entityManager.clear();

        assertThat(orderRepository.findById(expectedOrder.getId())).isEmpty();
    }

    @Test
    void update() {
        OrderEntity expectedOrder = orderRepository.findById(1).get();

        expectedOrder.setStatus(Status.CANCELED);
        orderRepository.update(expectedOrder);
        entityManager.clear();

        assertThat(orderRepository.findById(expectedOrder.getId()).get()).isEqualTo(expectedOrder);
    }

    @Test
    void findById() {
        OrderEntity expectedOrder = orderRepository.findById(1).get();
        entityManager.clear();

        assertThat(expectedOrder.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void findAll() {
        List<OrderEntity> expectedOrder = orderRepository.findAll();
        entityManager.clear();

        assertThat(expectedOrder).hasSize(2);
    }
}
