package com.sysc.workshop.user.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.user.UserDto;
import com.sysc.workshop.user.exception.EmailAlreadyExists;
import com.sysc.workshop.user.exception.UserNotFoundException;
import com.sysc.workshop.user.request.CreateUserRequest;
import com.sysc.workshop.user.request.UpdateUserRequest;
import com.sysc.workshop.user.service.IUserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<ApiResponse> createUser(
        @RequestBody CreateUserRequest request
    ) {
        try {
            UserDto user = userService.createUser(request);
            return ResponseEntity.status(201).body(
                new ApiResponse(user.getName() + " successfully created!", user)
            );
        } catch (EmailAlreadyExists e) {
            return ResponseEntity.status(400).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{userId}")
    ResponseEntity<ApiResponse> getUserById(@PathVariable UUID userId) {
        try {
            UserDto user = userService.getUserById(userId);
            return ResponseEntity.status(200).body(new ApiResponse("OK", user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @PutMapping("/")
    ResponseEntity<ApiResponse> updateUser(
        @RequestBody UpdateUserRequest request,
        @PathVariable UUID userID
    ) {
        try {
            UserDto user = userService.updateUserById(request, userID);
            return ResponseEntity.status(CREATED).body(
                new ApiResponse(user.getName() + " successfully updated!", user)
            );
        } catch (EmailAlreadyExists e) {
            return ResponseEntity.status(400).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/")
    ResponseEntity<ApiResponse> deleteUser(@RequestParam UUID userId) {
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.status(200).body(
                new ApiResponse("Successfully deleted!", null)
            );
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }
}
