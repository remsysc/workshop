package com.sysc.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkshopApplication.class, args);
    }
    // TODO: Implement GlobalExceptionHandler using @ControllerAdvice for API error responses
    // TODO: Add @AfterMapping in CategoryMapper to compute download URLs for images
    // TODO: Create ImageDto and CategoryDto for API responses
    // TODO: Add inventory checks in ProductService for better UX
    // TODO: Use @MapperConfig for shared mapper settings when needed
    // TODO: Implement pagination in ProductController endpoints
    // TODO: Return 201 status code for resource creation
}
