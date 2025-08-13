package com.sysc.workshop.product.service.product;

import com.sysc.workshop.cart.repository.CartItemRepository;
import com.sysc.workshop.core.exception.AlreadyExistsException;
import com.sysc.workshop.order.repository.OrderItemRepository;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.dto.product.ProductDto;
import com.sysc.workshop.product.dto.product.SearchProductRequestDTO;
import com.sysc.workshop.product.exception.ProductNotFoundException;
import com.sysc.workshop.product.mapper.ProductMapper;
import com.sysc.workshop.product.model.Category;
import com.sysc.workshop.product.model.Product;
import com.sysc.workshop.product.repository.CategoryRepository;
import com.sysc.workshop.product.repository.ProductRepository;
import com.sysc.workshop.product.request.AddProductRequest;
import com.sysc.workshop.product.request.UpdateProductRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ProductDto addProduct(AddProductRequest request) {
        if (productExists(request.getBrand(), request.getName())) {
            throw new AlreadyExistsException("Product Already Exists!");
        }

        //check if the category is found
        //if yes, set it as the new product's category;
        // if no, save it as category, then set it as the new product's category
        Category category = Optional.ofNullable(
            categoryRepository.findByName(request.getCategory().getName())
        ).orElseGet(() -> {
                Category newCategory = new Category(
                    request.getCategory().getName()
                );
                return categoryRepository.save(newCategory);
            });
        request.setCategory(category);
        Product product = createProduct(request, category);
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    private boolean productExists(String brand, String name) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    //creating new product via constructor
    private Product createProduct(
        AddProductRequest request,
        Category category
    ) {
        return new Product(
            request.getBrand(),
            request.getName(),
            request.getPrice(),
            request.getInventory(),
            request.getDescription(),
            category
        );
    }

    @Override
    public PagedResponseDTO<ProductDto> searchProducts(
        SearchProductRequestDTO request
    ) {
        PageRequest page = PageRequest.of(request.getPage(), request.getSize());
        Page<Product> productPage = productRepository.searchProducts(
            request.getName(),
            request.getBrand(),
            request.getCategory(),
            page
        );
        if (productPage.isEmpty()) {
            throw new ProductNotFoundException("");
        }
        Page<ProductDto> dtoPage = productPage.map(productMapper::toDto);
        return PagedResponseDTO.fromPage(dtoPage, dtoPage.getContent());
    }

    @Override
    public ProductDto updateProduct(UpdateProductRequest request, UUID id) {
        // tries to find the product,
        // if found, update and save,
        // if not, throw exception
        Product product = productRepository
            .findById(id)
            .map(existingProduct ->
                updateExistingProduct(existingProduct, request)
            )
            .map(productRepository::save)
            .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        return productMapper.toDto(product);
    }

    // simply replacing the existing product with the request
    private Product updateExistingProduct(
        Product existingProduct,
        UpdateProductRequest request
    ) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(
            request.getCategory().getName()
        );
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProductById(UUID id) {
        Product product = getProductEntityById(id);
        if (orderItemRepository.existsByProductId(id)) {
            throw new IllegalStateException(
                "Product has been ordered; cannot delete."
            );
        }
        product.setActive(false);
        productRepository.save(product);
        cartItemRepository.deleteAllByProductId(id);
    }

    @Override
    public ProductDto getProductById(UUID id) {
        Product product = productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        return productMapper.toDto(product);
    }

    @Override
    public PagedResponseDTO<ProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by("name").ascending()
        );
        Page<Product> pageResult = productRepository.findAll(pageable);
        List<ProductDto> productDtos = pageResult
            .map(productMapper::toDto)
            .toList();
        return PagedResponseDTO.fromPage(pageResult, productDtos);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    //entity service
    @Override
    public Product getProductEntityById(UUID id) {
        return productRepository
            .findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id.toString()));
    }
}
