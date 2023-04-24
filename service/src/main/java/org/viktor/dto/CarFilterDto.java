package org.viktor.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CarFilterDto {

    String brand;
    String model;
    Integer olderYearIssue;
    String colour;
    Integer minSeatsQuantity;
    String category;
    BigDecimal maxDayPrice;
}
