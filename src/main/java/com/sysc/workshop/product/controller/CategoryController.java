package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.dto.category.SearchCategoryRequestDTO;
import com.sysc.workshop.product.dto.category.SearchCategoryResponseDTO;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.exception.CategoryNotFoundException;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.product.service.category.ICategoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService iCategoryService;

    //TODO: Use DTO for request and response bodies

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        try {
            List<CategoryDto> categories = iCategoryService.getAllCategories();
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Success: ", categories)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(
                    "Error retrieving categories: " + e.getMessage()
                )
            );
        }
    }

    @GetMapping("/search")
    public ResponseEntity<
        ApiResponse<SearchCategoryResponseDTO>
    > searchCategory(@Validated SearchCategoryRequestDTO requestDTO) {
        try {
            // convert the request parameters to a paged response DTO;
            //search categories based on the request parameters
            PagedResponseDTO<CategoryDto> pagedResponse =
                iCategoryService.searchCategories(requestDTO);

            // convert the paged response to a response DTO
            SearchCategoryResponseDTO respose = new SearchCategoryResponseDTO(
                pagedResponse
            );

            return ResponseEntity.status(OK).body(
                ApiResponse.success("Search successful", respose)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(
                    "Error searching categories: " + e.getMessage()
                )
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> addCategory(
        @RequestParam String name
    ) {
        try {
            CategoryDto category = iCategoryService.addCategory(name);
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Added successfully!", category)
            );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(
                ApiResponse.error(e.getMessage())
            );
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
        @RequestParam String name,
        @PathVariable UUID productId
    ) {
        try {
            CategoryDto category = iCategoryService.updateCategory(
                name,
                productId
            );
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Updated successfully!", category)
            );
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> deleteCategoryById(
        @PathVariable UUID id
    ) {
        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success("Deleted successfully!", null)
            );
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(
        @PathVariable UUID id
    ) {
        try {
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.status(OK).body(
                ApiResponse.success("Found", category)
            );
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.error(e.getMessage())
            );
        }
    }
}
