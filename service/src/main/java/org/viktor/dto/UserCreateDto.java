package org.viktor.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.viktor.entity.Role;

@Value
@FieldNameConstants
public class UserCreateDto {

    String email;
    String password;
    Role role;
}
