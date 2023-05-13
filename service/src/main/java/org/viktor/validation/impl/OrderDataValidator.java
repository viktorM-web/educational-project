package org.viktor.validation.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.OrderCreateDto;
import org.viktor.entity.OrderEntity;
import org.viktor.repository.ClientDataRepository;
import org.viktor.repository.OrderRepository;
import org.viktor.validation.OrderData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class OrderDataValidator implements ConstraintValidator<OrderData, OrderCreateDto> {

    private final OrderRepository orderRepository;
    private final ClientDataRepository clientDataRepository;

    @Override
    public boolean isValid(OrderCreateDto value, ConstraintValidatorContext context) {
        var client = clientDataRepository.findByUserId(value.getUserId());
        if(client.isEmpty()){
            return false;
        }else{
            if(value.getStartDateUse().toLocalDate()
                    .isAfter(client.get().getDateExpiry())){
                return false;
            }
            var orders = orderRepository.findAllByCarId(value.getCarId());
            for (OrderEntity order : orders) {
                if (value.getStartDateUse().isAfter(order.getStartDateUse()) &&
                    value.getStartDateUse().isBefore(order.getExpirationDate()) ||
                    value.getExpirationDate().isAfter(order.getStartDateUse()) &&
                    value.getExpirationDate().isBefore(order.getExpirationDate())
                ) return false;
            }
            return true;
        }
    }
}
