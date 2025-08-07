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
import com.sysc.workshop.product.mapper.CategoryMapper;
import com.sysc.workshop.product.mapper.PagedResponseMapper;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.product.service.category.ICategoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService iCategoryService;

    //TODO: Use DTO for request and response bodies

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        try {
            List<Category> categories = iCategoryService.getAllCategories();
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
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.status(OK).body(
                new ApiResponse("Added successfully!", category)
            );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateCategory(
        @RequestBody Category name,
        @PathVariable UUID productId
    ) {
        try {
            Category category = iCategoryService.updateCategory(
                name,
                productId
            );
            return ResponseEntity.status(OK).body(
                new ApiResponse("Updated successfully!", category)
            );
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(
        @PathVariable UUID id
    ) {
        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.ok(
                new ApiResponse("Deleted successfully!", null)
            );
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable UUID id) {
        try {
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }
}
