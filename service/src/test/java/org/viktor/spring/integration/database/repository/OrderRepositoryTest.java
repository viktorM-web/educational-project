package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.dao.CarCategoryRepository;
import org.viktor.dao.CarRepository;
import org.viktor.dao.OrderRepository;
import org.viktor.dao.UserRepository;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.util.EntityUtil;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.Status;
import org.viktor.entity.UserEntity;
import org.viktor.spring.integration.annotation.IT;
import org.viktor.util.TestDataImporter;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class OrderRepositoryTest {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initData() {
        TestDataImporter.importData(entityManager);
    }

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
        entityManager.clear();

        assertThat(orderRepository.findById(expectedOrder.getId())).isEmpty();
    }

    @Test
    void update() {
        OrderEntity order = saveOrder();
        entityManager.clear();

        OrderEntity expectedOrder = orderRepository.findById(order.getId()).get();

        expectedOrder.setStatus(Status.CANCELED);
        orderRepository.update(expectedOrder);
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
