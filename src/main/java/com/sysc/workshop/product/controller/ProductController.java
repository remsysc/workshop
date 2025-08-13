package com.sysc.workshop.product.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.dto.product.ProductDto;
import com.sysc.workshop.product.dto.product.SearchProductRequestDTO;
import com.sysc.workshop.product.dto.product.SearchProductResponseDTO;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import com.sysc.workshop.product.service.product.IProductService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService iProductService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SearchProductResponseDTO>> searchProducts(
        @Valid SearchProductRequestDTO request
    ) {
        PagedResponseDTO<ProductDto> pagedResponseDTO =
            iProductService.searchProducts(request);

        // convert response to SearchResponseDto
        SearchProductResponseDTO response = new SearchProductResponseDTO(
            pagedResponseDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Success", response)
        );
    }

    @GetMapping
    public ResponseEntity<
        ApiResponse<PagedResponseDTO<ProductDto>>
    > getAllProducts(
        @Valid @RequestParam(defaultValue = "0") Integer page,
        @Valid @RequestParam(defaultValue = "10") Integer size
    ) {
        PagedResponseDTO<ProductDto> products = iProductService.getAllProducts(
            page,
            size
        );

        return ResponseEntity.ok(ApiResponse.success("Success", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(
        @Valid @PathVariable UUID id
    ) {
        ProductDto product = iProductService.getProductById(id);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Found", product)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> addProduct(
        @Valid @RequestBody AddProductRequest request
    ) {
        ProductDto product = iProductService.addProduct(request);
        return ResponseEntity.status(CREATED).body(
            ApiResponse.success(
                "Product: " + product.getName() + " successfully created!",
                product
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
        @Valid @RequestBody UpdateProductRequest request,
        @Valid @PathVariable UUID id
    ) {
        ProductDto product = iProductService.updateProduct(request, id);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Updated Successfully!", product)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProductById(
        @Valid @PathVariable UUID id
    ) {
        iProductService.deleteProductById(id);
        return ResponseEntity.status(OK).body(
            ApiResponse.success("Deleted Successfully!", null)
        );
    }

    // //TODO: Find a use case for this method or just remove it in the future
    // public ResponseEntity<ApiResponse> countProductsByBrandAndName(
    //     String brand,
    //     String name
    // ) {
    //     try {
    //         Long number = iProductService.countProductsByBrandAndName(
    //             brand,
    //             name
    //         );
    //         return ResponseEntity.ok(new ApiResponse("Success", number));
    //     } catch (Exception e) {
    //         return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    //     }
    // }
}
