package org.viktor.dao;

import org.springframework.stereotype.Repository;
import org.viktor.entity.CarCategoryEntity;

import javax.persistence.EntityManager;

@Repository
public class CarCategoryRepository extends RepositoryBase<Integer, CarCategoryEntity> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategoryEntity.class, entityManager);
    }
}
