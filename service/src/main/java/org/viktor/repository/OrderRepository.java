package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viktor.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
