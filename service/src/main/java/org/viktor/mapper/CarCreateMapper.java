package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.viktor.dto.CarCreateDto;
import org.viktor.entity.CarCategoryEntity;
import org.viktor.entity.CarEntity;
import org.viktor.repository.CarCategoryRepository;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class CarCreateMapper implements Mapper<CarCreateDto, CarEntity> {

    private final CarCategoryRepository carCategoryRepository;

    @Override
    public CarEntity map(CarCreateDto fromObject, CarEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public CarEntity map(CarCreateDto object) {
        var car = new CarEntity();
        copy(object, car);
        return car;
    }

    private void copy(CarCreateDto object, CarEntity car) {
        car.setVinCode(object.getVinCode());
        car.setBrand(object.getBrand());
        car.setModel(object.getModel());
        car.setYearIssue(object.getYearIssue());
        car.setColour(object.getColour());
        car.setSeatsQuantity(object.getSeatsQuantity());
        car.setCarCategory(getCarCategory(object.getCarCategoryId()));

        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> car.setImage(image.getOriginalFilename()));
    }

    private CarCategoryEntity getCarCategory(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(carCategoryRepository::findById)
                .orElse(null);
    }
}
