package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>,
        QuerydslPredicateExecutor<OrderEntity> {

    List<OrderEntity> findAllByCarId(Integer carId);
}
