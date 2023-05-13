package org.viktor.dto;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Value
public class CarCategoryCreateDto {

    @NotBlank(message = "category code shouldn't be empty")
    @Size(max = 32)
    String category;

    @DecimalMin("0.00")
    BigDecimal dayPrice;
}
