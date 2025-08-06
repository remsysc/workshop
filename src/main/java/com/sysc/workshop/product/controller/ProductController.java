package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.*;

import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.ProductDto;
import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import com.sysc.workshop.product.service.product.IProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService iProductService;

    //FIXME: This method is commented out because it has the same endpoint as the one below.
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchProducts(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ){
        List<ProductDto> products = iProductService.searchProducts(name, brand, category, page, size);
            return ResponseEntity.ok(new ApiResponse("Success", products));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable UUID id) {
        try {
            ProductDto product = iProductService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Found", product));
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
            return ResponseEntity.ok(
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
            return ResponseEntity.ok(
                new ApiResponse("Deleted Successfully!", null)
            );
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

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
