package com.sysc.workshop.product.service.product;

import com.sysc.workshop.product.dto.ProductDto;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;

import java.util.List;
import java.util.UUID;

public interface IProductService {
  ProductDto addProduct(AddProductRequest product);
  ProductDto getProductById(UUID id);
  Product getProductEntityById(UUID id); // server - server
  ProductDto updateProduct(UpdateProductRequest request, UUID id);
  void deleteProductById(UUID id);
  List<ProductDto> getAllProducts();
  List<ProductDto> getProductsByCategory(String category);
  List<ProductDto> getProductsByBrand(String brand);
  List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
  List<ProductDto> getProductsByName(String name);
  List<ProductDto> getProductsByBrandAndName(String brand, String name);
  Long countProductsByBrandAndName(String brand, String name);

  
}
