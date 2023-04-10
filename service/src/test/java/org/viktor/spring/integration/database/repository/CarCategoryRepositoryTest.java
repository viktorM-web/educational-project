package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.viktor.repository.CarCategoryRepository;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.spring.integration.IntegrationTestBase;
import org.viktor.util.EntityUtil;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CarCategoryRepositoryTest extends IntegrationTestBase {

    private final CarCategoryRepository carCategoryRepository;
    private final EntityManager entityManager;

    @Test
    void save() {
        CarCategoryEntity carCategory = saveCarCategory();

        assertThat(carCategory.getId()).isNotNull();
    }

    @Test
    void delete() {
        CarCategoryEntity carCategory = saveCarCategory();
        entityManager.clear();

        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(carCategory.getId()).get();

        carCategoryRepository.delete(expectedCarCategory);
        entityManager.flush();
        entityManager.clear();

        assertThat(carCategoryRepository.findById(expectedCarCategory.getId())).isEmpty();
    }

    @Test
    void update() {
        CarCategoryEntity carCategory = saveCarCategory();
        entityManager.clear();

        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(carCategory.getId()).get();

        expectedCarCategory.setCategory("economy+");
        carCategoryRepository.saveAndFlush(expectedCarCategory);

        entityManager.clear();

        assertThat(carCategoryRepository.findById(expectedCarCategory.getId()).get()).isEqualTo(expectedCarCategory);
    }

    @Test
    void findById() {
        CarCategoryEntity carCategory = saveCarCategory();
        entityManager.clear();

        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(carCategory.getId()).get();
        entityManager.clear();

        assertThat(expectedCarCategory.getCategory()).isEqualTo("test");
    }

    @Test
    void findAll() {
        List<CarCategoryEntity> expectedCarCategory = carCategoryRepository.findAll();
        entityManager.clear();

        assertThat(expectedCarCategory).hasSize(3);
    }

    private CarCategoryEntity saveCarCategory() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        carCategoryRepository.save(carCategory);
        return carCategory;
    }
}
