package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.CarCategoryCreateDto;
import org.viktor.entity.CarCategoryEntity;

@Component
@RequiredArgsConstructor
public class CarCategoryCreateMapper implements Mapper<CarCategoryCreateDto, CarCategoryEntity> {

    @Override
    public CarCategoryEntity map(CarCategoryCreateDto fromObject, CarCategoryEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public CarCategoryEntity map(CarCategoryCreateDto object) {
        var carCategory = new CarCategoryEntity();
        copy(object, carCategory);
        return carCategory;
    }

    private void copy(CarCategoryCreateDto object, CarCategoryEntity carCategory) {
        carCategory.setCategory(object.getCategory());
        carCategory.setDayPrice(object.getDayPrice());

    }
}
