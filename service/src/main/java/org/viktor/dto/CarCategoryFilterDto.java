package org.viktor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CarCategoryFilterDto {

    String category;
    BigDecimal dayPrice;
}
