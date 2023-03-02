package org.viktor.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarCategoryIT extends EntityTestBase {

    @Test
    void saveCarCategory() {
        CarCategoryEntity carCategory = EntityUtil.buildCarCategory();

        session.save(carCategory);
        session.flush();

        assertThat(carCategory.getId()).isNotNull();
    }

    @Test
    void getCarCategory() {
        CarCategoryEntity expectedCarCategory = EntityUtil.buildCarCategory();
        session.save(expectedCarCategory);
        session.flush();
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isEqualTo(expectedCarCategory);
    }

    @Test
    void update() {
        CarCategoryEntity expectedCarCategory = EntityUtil.buildCarCategory();
        session.save(expectedCarCategory);
        session.flush();
        expectedCarCategory.setCategory("E");
        session.update(expectedCarCategory);
        session.flush();
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isEqualTo(expectedCarCategory);
    }

    @Test
    void delete() {
        CarCategoryEntity expectedCarCategory = EntityUtil.buildCarCategory();
        session.save(expectedCarCategory);
        session.flush();
        session.clear();

        session.delete(expectedCarCategory);
        session.flush();
        session.clear();

        CarCategoryEntity actualCarCategory = session.get(CarCategoryEntity.class, expectedCarCategory.getId());

        assertThat(actualCarCategory).isNull();
    }
}
