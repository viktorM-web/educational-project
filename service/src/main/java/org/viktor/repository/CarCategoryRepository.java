package org.viktor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.viktor.entity.CarCategoryEntity;

public interface CarCategoryRepository extends JpaRepository<CarCategoryEntity, Integer> {

}
