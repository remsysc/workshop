package com.sysc.workshop.user.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.user.UserDto;
import com.sysc.workshop.user.request.CreateUserRequest;
import com.sysc.workshop.user.request.UpdateUserRequest;
import com.sysc.workshop.user.service.IUserService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final IUserService userService;

    //create user
    //get user/s
    //delete user
    // update user

    @PostMapping("/")
    ResponseEntity<ApiResponse<UserDto>> createUser(
        @Valid @RequestBody CreateUserRequest request
    ) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.status(CREATED).body(
            ApiResponse.success(user.getName() + " successfully created!", user)
        );
    }

    @GetMapping("/{userId}")
    ResponseEntity<ApiResponse<UserDto>> getUserById(
        @PathVariable UUID userId
    ) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Success!", user)
        );
    }

    @PutMapping("/")
    ResponseEntity<ApiResponse<UserDto>> updateUser(
        @RequestBody UpdateUserRequest request,
        @PathVariable UUID userID
    ) {
        UserDto user = userService.updateUserById(request, userID);
        return ResponseEntity.status(OK).body(
            ApiResponse.success(user.getName() + " successfully updated!", user)
        );
    }

    @DeleteMapping("/")
    ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(NO_CONTENT).body(
            ApiResponse.success("Successfully deleted!", null)
        );
    }
}
