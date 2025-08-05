package com.sysc.workshop.user;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {

    private UUID id;
    private String name;
    private String email;
}
