package org.viktor.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.viktor.dto.ExtraPaymentCreateDto;
import org.viktor.entity.ExtraPaymentEntity;
import org.viktor.entity.OrderEntity;
import org.viktor.repository.OrderRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExtraPaymentCreateMapper implements Mapper<ExtraPaymentCreateDto, ExtraPaymentEntity> {

    private final OrderRepository orderRepository;

    @Override
    public ExtraPaymentEntity map(ExtraPaymentCreateDto fromObject, ExtraPaymentEntity toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public ExtraPaymentEntity map(ExtraPaymentCreateDto object) {
        var payment = new ExtraPaymentEntity();
        copy(object, payment);
        return payment;
    }

    private void copy(ExtraPaymentCreateDto object, ExtraPaymentEntity payment) {
        payment.setOrder(getUser(object.getOrderId()));
        payment.setDescription(object.getDescription());
        payment.setPrice(object.getPrice());
    }

    private OrderEntity getUser(Integer orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(orderRepository::findById)
                .orElse(null);
    }
}
