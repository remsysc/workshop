package com.sysc.workshop.user.request;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;
}
