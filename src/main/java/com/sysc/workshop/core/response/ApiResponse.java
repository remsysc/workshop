package com.sysc.workshop.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//response back to front end / client
public class ApiResponse<T> {

    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(msg, data);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(message, data);
    }
}
