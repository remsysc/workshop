package com.sysc.workshop.product.controller;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.product.exception.CategoryNotFoundException;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService iCategoryService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = iCategoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!", categories));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/name")
    public  ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @PostMapping("/")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added successfully!", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{productId}")
    public  ResponseEntity<ApiResponse> updateCategory(@RequestBody Category name, @PathVariable UUID productId){
        try {
            Category category = iCategoryService.updateCategory(name, productId);
            return ResponseEntity.ok(new ApiResponse("Updated successfully!", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
@DeleteMapping("/")
    public ResponseEntity<ApiResponse> deleteCategoryById(@RequestParam UUID id){

        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted successfully!",null));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/{id}")

    public  ResponseEntity<ApiResponse> getCategoryById(@PathVariable UUID id){
        try {
            Category category = iCategoryService.getCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
