package org.viktor.dto;

import lombok.Value;
import org.viktor.entity.Status;

import java.time.LocalDateTime;

@Value
public class OrderFilter {

    Integer userId;
    Integer carId;
    LocalDateTime startDateUse;
    LocalDateTime expirationDate;
    Status status;
}
