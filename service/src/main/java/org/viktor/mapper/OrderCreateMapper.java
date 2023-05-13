package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.OrderCreateDto;
import org.viktor.entity.CarEntity;
import org.viktor.entity.OrderEntity;
import org.viktor.entity.UserEntity;
import org.viktor.repository.CarRepository;
import org.viktor.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateMapper implements Mapper<OrderCreateDto, OrderEntity> {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public OrderEntity map(OrderCreateDto fromObject, OrderEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public OrderEntity map(OrderCreateDto object) {
        var order = new OrderEntity();
        copy(object, order);
        return order;
    }

    private void copy(OrderCreateDto object, OrderEntity order) {
        order.setUser(getUser(object.getUserId()));
        order.setCar(getCar(object.getCarId()));
        order.setStartDateUse(object.getStartDateUse());
        order.setExpirationDate(object.getExpirationDate());
        order.setStatus(object.getStatus());
    }

    private UserEntity getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

    private CarEntity getCar(Integer carId) {
        return Optional.ofNullable(carId)
                .flatMap(carRepository::findById)
                .orElse(null);
    }
}
