package com.sysc.workshop.product.service.product;

import com.sysc.workshop.product.dto.ProductDto;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProductService {
    ProductDto addProduct(AddProductRequest product);
    ProductDto getProductById(UUID id);
    Product getProductEntityById(UUID id); // server - server
    ProductDto updateProduct(UpdateProductRequest request, UUID id);
    void deleteProductById(UUID id);
    List<ProductDto> getAllProducts();
    List<ProductDto> searchProducts(
        String name,
        String brand,
        String category,
       Pageable pageable
    );

    Long countProductsByBrandAndName(String brand, String name);
}
