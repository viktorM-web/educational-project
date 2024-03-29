package org.viktor.dto;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Value
public class ExtraPaymentCreateDto {

    @Positive
    Integer orderId;

    @NotNull
    String description;

    @DecimalMin("0.00")
    BigDecimal price;
}
