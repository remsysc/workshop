package com.sysc.workshop.user.service;

import com.sysc.workshop.user.UserDto;
import com.sysc.workshop.user.model.User;
import com.sysc.workshop.user.request.CreateUserRequest;
import com.sysc.workshop.user.request.UpdateUserRequest;

import java.util.UUID;

public interface IUserService {

    UserDto createUser(CreateUserRequest request);
    User getUserEntityById(UUID userId);
    UserDto getUserById(UUID userId);
    void deleteUserById(UUID userId);
    UserDto updateUserById(UpdateUserRequest request, UUID userId);

    User getAuthenticatedUser();
}
