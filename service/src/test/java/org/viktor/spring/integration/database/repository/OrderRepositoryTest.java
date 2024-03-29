package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.viktor.repository.CarCategoryRepository;
import org.viktor.repository.CarRepository;
import org.viktor.repository.OrderRepository;
import org.viktor.repository.UserRepository;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.spring.integration.IntegrationTestBase;
import org.viktor.util.EntityUtil;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.Status;
import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class OrderRepositoryTest extends IntegrationTestBase {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        OrderEntity order = saveOrder();

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void delete() {
        OrderEntity order = saveOrder();
        entityManager.clear();

        OrderEntity expectedOrder = orderRepository.findById(order.getId()).get();

        orderRepository.delete(expectedOrder);
        entityManager.flush();
        entityManager.clear();

        assertThat(orderRepository.findById(expectedOrder.getId())).isEmpty();
    }

    @Test
    void update() {
        OrderEntity order = saveOrder();
        entityManager.clear();

        OrderEntity expectedOrder = orderRepository.findById(order.getId()).get();

        expectedOrder.setStatus(Status.CANCELED);
        orderRepository.saveAndFlush(expectedOrder);
        entityManager.clear();

        assertThat(orderRepository.findById(expectedOrder.getId()).get()).isEqualTo(expectedOrder);
    }

    @Test
    void findById() {
        OrderEntity order = saveOrder();
        entityManager.clear();

        OrderEntity expectedOrder = orderRepository.findById(order.getId()).get();
        entityManager.clear();

        assertThat(expectedOrder.getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void findAll() {
        List<OrderEntity> expectedOrder = orderRepository.findAll();
        entityManager.clear();

        assertThat(expectedOrder).hasSize(2);
    }

    private OrderEntity saveOrder() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        carCategoryRepository.save(carCategory);
        CarEntity car = EntityUtil.buildCar(carCategory);
        carRepository.save(car);
        UserEntity user = EntityUtil.buildUser();
        userRepository.save(user);
        OrderEntity order = EntityUtil.buildOrder(user, car);
        orderRepository.save(order);
        return order;
    }
}
