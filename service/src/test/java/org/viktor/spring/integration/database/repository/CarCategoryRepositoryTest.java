package org.viktor.spring.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.viktor.dao.CarCategoryRepository;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.util.EntityUtil;
import org.viktor.spring.integration.annotation.IT;
import org.viktor.util.TestDataImporter;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class CarCategoryRepositoryTest {

    private final CarCategoryRepository carCategoryRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initData() {
        TestDataImporter.importData(entityManager);
    }

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
        entityManager.clear();

        assertThat(carCategoryRepository.findById(expectedCarCategory.getId())).isEmpty();
    }

    @Test
    void update() {
        CarCategoryEntity carCategory = saveCarCategory();
        entityManager.clear();

        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(carCategory.getId()).get();

        expectedCarCategory.setCategory("economy+");
        carCategoryRepository.update(expectedCarCategory);
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
