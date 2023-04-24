package org.viktor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ExtraPaymentFilter {

    Integer orderId;
    String description;
    BigDecimal price;
}
