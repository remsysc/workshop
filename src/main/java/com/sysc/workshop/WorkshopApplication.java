package com.sysc.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication {

	public static void main(String[] args) {

		SpringApplication.run(WorkshopApplication.class, args);
	}
	// create own exception handles for api
	// @ControllerAdvice


	//compute download url using @afterMapping
	// category mapper / dto
	// image dto for all methods

	// A
	// soft checks on service layer for ux purpose , inventory checks etc
	//Use mappers only when crossing the API boundary.

	//implements

//	// API-facing
//	public interface ProductApiService {
//		ProductDto getProductById(Long id);
//		// …all methods returning DTO
//	}
//
//	// Domain-level
//	public interface ProductDomainService {
//		Product findEntityById(Long id);
//		// …domain operations on Product
//	}
// add when necessary @MapperConfig
	// paging when needed

	// FIX STATUS CODE 201 WHEN CREATED ETC NOT JUST 200
	// THERE SHOULD BE NO INTERNAL SERVER ERROR AS RESPONSE
}
