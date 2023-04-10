package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.viktor.repository.CarCategoryRepository;
import org.viktor.repository.CarRepository;
import org.viktor.repository.ExtraPaymentRepository;
import org.viktor.repository.OrderRepository;
import org.viktor.repository.UserRepository;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.spring.integration.IntegrationTestBase;
import org.viktor.util.EntityUtil;
import org.viktor.entity.ExtraPaymentEntity;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.UserEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class ExtraPaymentRepositoryTest extends IntegrationTestBase {

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ExtraPaymentRepository extraPaymentRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        ExtraPaymentEntity extraPayment = savePayment();

        assertThat(extraPayment.getId()).isNotNull();
    }

    @Test
    void delete() {
        ExtraPaymentEntity extraPayment = savePayment();
        entityManager.clear();

        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(extraPayment.getId()).get();

        extraPaymentRepository.delete(expectedExtraPayment);
        entityManager.flush();
        entityManager.clear();

        assertThat(extraPaymentRepository.findById(expectedExtraPayment.getId())).isEmpty();
    }

    @Test
    void update() {
        ExtraPaymentEntity extraPayment = savePayment();
        entityManager.clear();

        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(extraPayment.getId()).get();

        expectedExtraPayment.setDescription("speeding fine 2023.1.4 14:00 99.99$");
        extraPaymentRepository.saveAndFlush(expectedExtraPayment);
        entityManager.clear();

        assertThat(extraPaymentRepository.findById(expectedExtraPayment.getId()).get()).isEqualTo(expectedExtraPayment);
    }

    @Test
    void findById() {
        ExtraPaymentEntity extraPayment = savePayment();
        entityManager.clear();

        ExtraPaymentEntity expectedExtraPayment = extraPaymentRepository.findById(extraPayment.getId()).get();
        entityManager.clear();

        assertThat(expectedExtraPayment.getDescription()).isEqualTo("test");
    }

    @Test
    void findAll() {
        List<ExtraPaymentEntity> expectedExtraPayment = extraPaymentRepository.findAll();
        entityManager.clear();

        assertThat(expectedExtraPayment).hasSize(1);
    }

    private ExtraPaymentEntity savePayment() {
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
        return extraPayment;
    }
}
