package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.CarEntity;

public interface CarRepository extends JpaRepository<CarEntity, Integer>,
        FilterCarRepository,
        QuerydslPredicateExecutor<CarEntity> {

}
