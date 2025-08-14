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
    // DONE: Create ImageDto and CategoryDto for API responses (current)
    // TODO: Use @MapperConfig for shared mapper settings when needed
    // TODO: Implement pagination in Order and cart endpoints
    // DONE: Refine all endpoints
}
