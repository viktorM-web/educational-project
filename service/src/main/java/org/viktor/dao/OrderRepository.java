package org.viktor.dao;

import org.viktor.entity.OrderEntity;

import javax.persistence.EntityManager;

public class OrderRepository extends RepositoryBase<Integer, OrderEntity> {
    public OrderRepository(EntityManager entityManager) {
        super(OrderEntity.class, entityManager);
    }
}
