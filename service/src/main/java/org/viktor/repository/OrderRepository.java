package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.UserEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>,
                                            QuerydslPredicateExecutor<OrderEntity> {
}
