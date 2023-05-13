package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.ExtraPaymentEntity;

import java.util.Optional;

public interface ExtraPaymentRepository extends JpaRepository<ExtraPaymentEntity, Integer>,
        QuerydslPredicateExecutor<ExtraPaymentEntity> {

    Optional<ExtraPaymentEntity> findByOrderId(Integer integer);
}
