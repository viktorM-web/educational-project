package org.viktor.dto;

import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.viktor.entity.Status;
import org.viktor.validation.OrderData;
import org.viktor.validation.group.CreateAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Value
@OrderData(groups = CreateAction.class)
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
