package org.viktor.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CarFilterDto {

    String brand;
    String model;
    Integer yearIssue;
    String colour;
    Integer seatsQuantity;
    String category;
    BigDecimal dayPrice;

}
