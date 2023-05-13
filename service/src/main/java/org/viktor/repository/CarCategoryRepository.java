package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.UserEntity;

public interface CarCategoryRepository extends JpaRepository<CarCategoryEntity, Integer>,
                                                QuerydslPredicateExecutor<CarCategoryEntity> {

}
