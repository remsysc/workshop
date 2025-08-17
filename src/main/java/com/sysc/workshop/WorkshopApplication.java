package com.sysc.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopApplication.class, args);
    }
    // DONE: Implement GlobalExceptionHandler using @ControllerAdvice for API error responses
    // TODO: Add @AfterMapping in CategoryMapper to compute download URLs for images
    // DONE: Create ImageDto and CategoryDto for API responses
    // TODO: Use @MapperConfig for shared mapper settings when needed
    // TODO: Implement pagination in Order and cart endpointsss
    // DONE: Refine all endpoints
    // TODO: Implements OAUTH2 / OIDC
    // TODO: Fix all endpoints so that only authenticated users can access what
    // TODO: Add exceptions of security to global Advice
    // DONE: create auth controller and log in request for login / logout
    // TODO: finish this back end with relative simple security
    // FUCK IM ALMOST DONE WITH THIS BACK END!!!!!!!!
}
