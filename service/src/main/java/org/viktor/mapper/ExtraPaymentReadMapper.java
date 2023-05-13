package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.ExtraPaymentReadDto;
import org.viktor.entity.ExtraPaymentEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExtraPaymentReadMapper implements Mapper<ExtraPaymentEntity, ExtraPaymentReadDto> {

    private final OrderReadMapper orderReadMapper;

    @Override
    public ExtraPaymentReadDto map(ExtraPaymentEntity object) {
        var orderReadDto = Optional.ofNullable(object.getOrder())
                .map(orderReadMapper::map)
                .orElse(null);

        return new ExtraPaymentReadDto(
                object.getId(),
                orderReadDto,
                object.getDescription(),
                object.getPrice()
        );
    }
}
