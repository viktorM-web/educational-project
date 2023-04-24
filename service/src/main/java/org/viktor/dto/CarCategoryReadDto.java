package org.viktor.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CarCategoryReadDto {

    Integer id;
    String category;
    BigDecimal dayPrice;
}
