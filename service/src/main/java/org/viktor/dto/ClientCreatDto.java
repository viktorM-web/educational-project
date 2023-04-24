package org.viktor.dto;

import lombok.Value;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value

public class ClientCreatDto {

    @Positive
    Integer userId;

    @NotBlank(message = "firstname shouldn't be empty")
    @Size(max = 128)
    String firstname;

    @NotBlank(message = "lastname shouldn't be empty")
    @Size(max = 128)
    String lastname;

    @Past
    LocalDate birthday;

    @NotBlank(message = "number driver licence shouldn't be empty")
    @Size(max = 128)
    String driverLicenceNo;

    @Future
    LocalDate dateExpiry;

    @Positive
    Integer driverExperience;
}
