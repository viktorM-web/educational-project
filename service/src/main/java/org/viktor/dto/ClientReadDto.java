package org.viktor.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ClientReadDto {

    Integer id;
    UserReadDto user;
    String firstname;
    String lastname;
    LocalDate birthday;
    String driverLicenceNo;
    LocalDate dateExpiry;
    Integer driverExperience;
}
