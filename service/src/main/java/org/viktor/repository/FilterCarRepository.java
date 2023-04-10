package org.viktor.repository;

import org.viktor.dto.CarFilterDto;
import org.viktor.entity.CarEntity;

import java.util.List;

public interface FilterCarRepository {

    List<CarEntity> findAllByFilter(CarFilterDto filter);
}
