package com.sysc.workshop.user;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String name;
    private String email;
}
