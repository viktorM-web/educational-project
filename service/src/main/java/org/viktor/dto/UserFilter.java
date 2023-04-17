package org.viktor.dto;

import lombok.Value;
import org.viktor.entity.Role;

@Value
public class UserFilter {

    String email;
    Role role;
}
