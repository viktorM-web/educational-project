package org.viktor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExtraPaymentReadDto {

    Integer id;
    OrderReadDto order;
    String description;
    BigDecimal price;
}
