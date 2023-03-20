package org.viktor.dao;

import org.viktor.entity.ExtraPaymentEntity;

import javax.persistence.EntityManager;

public class ExtraPaymentRepository extends RepositoryBase<Integer, ExtraPaymentEntity> {
    public ExtraPaymentRepository(EntityManager entityManager) {
        super(ExtraPaymentEntity.class, entityManager);
    }
}
