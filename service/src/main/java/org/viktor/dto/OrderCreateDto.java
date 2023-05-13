package org.viktor.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.viktor.entity.Status;
import org.viktor.validation.OrderData;
import org.viktor.validation.group.CreateAction;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Value
@FieldNameConstants
@OrderData(groups = CreateAction.class)
public class OrderCreateDto {

    @NotNull
    @Positive
    Integer userId;

    @NotNull
    @Positive
    Integer carId;

    @NotNull
    @Future
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startDateUse;

    @NotNull
    @Future
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime expirationDate;

    @NotNull
    Status status;
}
