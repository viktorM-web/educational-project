package org.viktor.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Value
@FieldNameConstants
public class CarCreateDto {

    @NotBlank(message = "VIN code shouldn't be empty")
    @Size(max = 128)
    String vinCode;

    @NotBlank(message = "brand shouldn't be empty")
    @Size(max = 128)
    String brand;

    @NotBlank(message = "brand shouldn't be empty")
    @Size(max = 128)
    String model;

    @Min(2018)
    Integer yearIssue;

    @NotBlank(message = "colour shouldn't be empty")
    @Size(max = 128)
    String colour;

    @Min(1)
    Integer seatsQuantity;
    MultipartFile image;

    @Positive
    Integer carCategoryId;
}
