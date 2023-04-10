package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viktor.entity.ExtraPaymentEntity;

public interface ExtraPaymentRepository extends JpaRepository<ExtraPaymentEntity, Integer> {

}
