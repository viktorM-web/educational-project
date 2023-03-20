package org.viktor.dao;

import org.junit.jupiter.api.Test;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.EntityUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CarCategoryRepositoryTest extends RepositoryTestBase {

    private static final CarCategoryRepository carCategoryRepository = new CarCategoryRepository(session);

    @Test
    void save() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();
        carCategoryRepository.save(carCategory);

        assertThat(carCategory.getId()).isNotNull();
    }

    @Test
    void delete() {
        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(1).get();

        carCategoryRepository.delete(expectedCarCategory);
        session.clear();

        assertThat(carCategoryRepository.findById(expectedCarCategory.getId())).isEmpty();
    }

    @Test
    void update() {
        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(1).get();

        expectedCarCategory.setCategory("economy+");
        carCategoryRepository.update(expectedCarCategory);
        session.clear();

        assertThat(carCategoryRepository.findById(expectedCarCategory.getId()).get()).isEqualTo(expectedCarCategory);
    }

    @Test
    void findById() {
        CarCategoryEntity expectedCarCategory = carCategoryRepository.findById(1).get();
        session.clear();

        assertThat(expectedCarCategory.getCategory()).isEqualTo("economy");
    }

    @Test
    void findAll() {
        List<CarCategoryEntity> expectedCarCategory = carCategoryRepository.findAll();
        session.clear();

        assertThat(expectedCarCategory).hasSize(3);
    }
}
