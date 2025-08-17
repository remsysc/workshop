package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.annotation.IsAdmin;
import com.sysc.workshop.core.annotation.IsViewer;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.dto.category.SearchCategoryRequestDTO;
import com.sysc.workshop.product.dto.category.SearchCategoryResponseDTO;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.service.category.ICategoryService;
import jakarta.validation.Valid;
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
@Validated
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService iCategoryService;

    @IsViewer
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = iCategoryService.getAllCategories();
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Success: ", categories)
        );
    }

    @IsViewer
    @GetMapping("/search")
    public ResponseEntity<
        ApiResponse<SearchCategoryResponseDTO>
    > searchCategory(@Valid SearchCategoryRequestDTO requestDTO) {
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
    }

    @IsAdmin
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> addCategory(
        @Valid @RequestParam String name
    ) {
        CategoryDto category = iCategoryService.addCategory(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("Added successfully!", category)
        );
    }

    @IsAdmin
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
        @Valid @RequestParam String name,
        @Valid @PathVariable UUID productId
    ) {
        CategoryDto category = iCategoryService.updateCategory(name, productId);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Updated successfully!", category)
        );
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> deleteCategoryById(
        @Valid @PathVariable UUID id
    ) {
        iCategoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Deleted successfully!", null)
        );
    }

    @IsAdmin
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(
        @Valid @PathVariable UUID id
    ) {
        CategoryDto category = iCategoryService.getCategoryById(id);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Found", category)
        );
    }
}
