package com.sysc.workshop.core.exception;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.exception.CategoryAlreadyExists;
import com.sysc.workshop.product.exception.CategoryNotFoundException;
import com.sysc.workshop.product.exception.ImageNotFoundException;
import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.user.exception.EmailAlreadyExists;
import com.sysc.workshop.user.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //GLOBAL EXCEPTION HANDLERS;
    // use logs and never expose 500 to client
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleExistingConflict(AlreadyExistsException e) {
        return ApiResponse.error(e.getMessage(), null); //change the message if going live
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGenericException(Exception e) {
        return ApiResponse.error(e.getMessage(), null);
    }

    // 400: @RequestBody validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleBodyValidation(
        MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (msg1, msg2) -> msg1
                )
            );

        return ApiResponse.error("Validation failed", errors);
    }

    // 400: @Validated parameter violations
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleParamValidation(
        ConstraintViolationException ex
    ) {
        Map<String, String> errors = ex
            .getConstraintViolations()
            .stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(),
                    ConstraintViolation::getMessage
                )
            );

        return ApiResponse.error("Validation failed", errors);
    }

    // 400: malformed JSON bodies
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMalformedJson(
        HttpMessageNotReadableException ex
    ) {
        return ApiResponse.error("Malformed JSON request", null);
    }

    // 400: missing query/form parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMissingParam(
        MissingServletRequestParameterException ex
    ) {
        String message = "Missing parameter: " + ex.getParameterName();
        return ApiResponse.error(message, null);
    }

    //USER EXCEPTION HANDLERS
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException e) {
        return ApiResponse.error(e.getMessage(), null);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleEmailAlreadyExists(EmailAlreadyExists e) {
        return ApiResponse.error(e.getMessage(), null);
    }

    //PRODUCT EXCEPTION HANDLERS
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleProductNotFound(ProductNotFoundException e) {
        return ApiResponse.error(e.getMessage(), null);
    }

    //CATEGORY EXCEPTION HANDLERS
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFound(
        CategoryNotFoundException e
    ) {
        return ApiResponse.error(e.getMessage(), null);
    }

    @ExceptionHandler(CategoryAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryConflict(CategoryAlreadyExists e) {
        return ApiResponse.error(e.getMessage(), null);
    }

    //IMAGE EXCEPTION HANDLERS
    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleImageNotFound(ImageNotFoundException e) {
        return ApiResponse.error(e.getMessage(), null);
    }
}
