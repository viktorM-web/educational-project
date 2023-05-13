package org.viktor.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.viktor.entity.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@FieldNameConstants
public class UserCreateDto {

    @Email
    String email;

    @NotBlank(message = "password shouldn't be empty")
    String password;

    @NotNull
    Role role;
}
