package org.viktor.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class LoginDto {

    @Email
    String email;

    @NotBlank(message = "password shouldn't be empty")
    String password;
}
