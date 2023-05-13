package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.CarReadDto;
import org.viktor.entity.CarEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarReadMapper implements Mapper<CarEntity, CarReadDto> {

    private final CarCategoryReadMapper carCategoryReadMapper;

    @Override
    public CarReadDto map(CarEntity object) {
        var carCategoryReadDto = Optional.ofNullable(object.getCarCategory())
                .map(carCategoryReadMapper::map)
                .orElse(null);

        return new CarReadDto(
                object.getId(),
                object.getVinCode(),
                object.getBrand(),
                object.getModel(),
                object.getYearIssue(),
                object.getColour(),
                object.getSeatsQuantity(),
                object.getImage(),
                carCategoryReadDto
        );
    }
}
