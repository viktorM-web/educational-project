package org.viktor.dao;

import org.springframework.stereotype.Repository;
import org.viktor.entity.OrderEntity;

import javax.persistence.EntityManager;

@Repository
public class OrderRepository extends RepositoryBase<Integer, OrderEntity> {

    public OrderRepository(EntityManager entityManager) {
        super(OrderEntity.class, entityManager);
    }
}
