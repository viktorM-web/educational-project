package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.CarEntity;
import org.viktor.entity.UserEntity;

public interface CarRepository extends JpaRepository<CarEntity, Integer>,
                                        FilterCarRepository,
                                        QuerydslPredicateExecutor<CarEntity> {

}
