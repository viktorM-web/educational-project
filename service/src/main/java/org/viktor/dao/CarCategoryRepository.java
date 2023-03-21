package org.viktor.dao;

import org.viktor.entity.CarCategoryEntity;

import javax.persistence.EntityManager;

public class CarCategoryRepository extends RepositoryBase<Integer, CarCategoryEntity> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategoryEntity.class, entityManager);
    }
}
