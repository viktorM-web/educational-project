package org.viktor.dto;

import lombok.Value;
import org.viktor.entity.Status;

import java.time.LocalDateTime;

@Value
public class OrderReadDto {

    Integer id;
    UserReadDto user;
    CarReadDto car;
    LocalDateTime startDateUse;
    LocalDateTime expirationDate;
    Status status;
}
