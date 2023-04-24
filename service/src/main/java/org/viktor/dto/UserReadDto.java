package org.viktor.dto;

import lombok.Value;
import org.viktor.entity.Role;

@Value
public class UserReadDto {

    Integer id;
    String email;
    String password;
    Role role;
}
