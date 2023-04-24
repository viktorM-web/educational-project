package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.OrderReadDto;
import org.viktor.entity.OrderEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<OrderEntity, OrderReadDto> {

    private final UserReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public OrderReadDto map(OrderEntity object) {
        var userReadDto = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);

        var carReadDto = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);

        return new OrderReadDto(
                object.getId(),
                userReadDto,
                carReadDto,
                object.getStartDateUse(),
                object.getExpirationDate(),
                object.getStatus()
        );
    }
}
