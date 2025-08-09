package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.product.ProductDto;
import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import com.sysc.workshop.product.service.product.IProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService iProductService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ProductDto>> searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String category,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductDto> products = iProductService.searchProducts(
            name,
            brand,
            category,
            pageable
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse("Success", products)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable UUID id) {
        try {
            ProductDto product = iProductService.getProductById(id);
            return ResponseEntity.status(OK).body(
                new ApiResponse("Found", product)
            );
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse("Not Found!", null)
            );
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        try {
            List<ProductDto> products = iProductService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR)
            );
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addProduct(
        @RequestBody AddProductRequest request
    ) {
        try {
            ProductDto product = iProductService.addProduct(request);
            return ResponseEntity.status(CREATED).body(
                new ApiResponse(
                    "Product: " + product.getName() + " successfully created!",
                    product
                )
            );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProduct(
        @RequestBody UpdateProductRequest request,
        @PathVariable UUID id
    ) {
        try {
            ProductDto product = iProductService.updateProduct(request, id);
            return ResponseEntity.status(OK).body(
                new ApiResponse("Updated Successfully!", product)
            );
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProductById(
        @PathVariable UUID id
    ) {
        try {
            iProductService.deleteProductById(id);
            return ResponseEntity.status(OK).body(
                new ApiResponse("Deleted Successfully!", null)
            );
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    //TODO: Find a use case for this method or just remove it in the future
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
        String brand,
        String name
    ) {
        try {
            Long number = iProductService.countProductsByBrandAndName(
                brand,
                name
            );
            return ResponseEntity.ok(new ApiResponse("Success", number));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }
}
