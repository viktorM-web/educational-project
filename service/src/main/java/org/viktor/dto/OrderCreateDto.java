package org.viktor.dto;

import lombok.Value;
import org.viktor.entity.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Value
public class OrderCreateDto {

    @Positive
    Integer userId;

    @Positive
    Integer carId;

    @Future
    LocalDateTime startDateUse;

    @Future
    LocalDateTime expirationDate;

    @NotNull
    Status status;
}
