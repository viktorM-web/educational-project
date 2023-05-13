package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.CarCategoryReadDto;
import org.viktor.entity.CarCategoryEntity;

@Component
@RequiredArgsConstructor
public class CarCategoryReadMapper implements Mapper<CarCategoryEntity, CarCategoryReadDto> {

    @Override
    public CarCategoryReadDto map(CarCategoryEntity object) {
        return new CarCategoryReadDto(
                object.getId(),
                object.getCategory(),
                object.getDayPrice()
        );
    }
}
