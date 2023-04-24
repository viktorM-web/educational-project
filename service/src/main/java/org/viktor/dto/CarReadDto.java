package org.viktor.dto;

import lombok.Value;

@Value
public class CarReadDto {

    Integer id;
    String vinCode;
    String brand;
    String model;
    Integer yearIssue;
    String colour;
    Integer seatsQuantity;
    String image;
    CarCategoryReadDto carCategory;
}
