package org.viktor.dao;

import org.springframework.stereotype.Repository;
import org.viktor.entity.ExtraPaymentEntity;

import javax.persistence.EntityManager;

@Repository
public class ExtraPaymentRepository extends RepositoryBase<Integer, ExtraPaymentEntity> {

    public ExtraPaymentRepository(EntityManager entityManager) {
        super(ExtraPaymentEntity.class, entityManager);
    }
}
