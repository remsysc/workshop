package com.sysc.workshop.product.service.product;

import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.dto.product.ProductDto;
import com.sysc.workshop.product.dto.product.SearchProductRequestDTO;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import java.util.UUID;

public interface IProductService {
    ProductDto addProduct(AddProductRequest product);
    ProductDto getProductById(UUID id);
    Product getProductEntityById(UUID id); // server - server
    ProductDto updateProduct(UpdateProductRequest request, UUID id);
    void deleteProductById(UUID id);
    PagedResponseDTO<ProductDto> getAllProducts(int page, int size);
    PagedResponseDTO<ProductDto> searchProducts(
        SearchProductRequestDTO requestDTO
    );

    Long countProductsByBrandAndName(String brand, String name);
}
