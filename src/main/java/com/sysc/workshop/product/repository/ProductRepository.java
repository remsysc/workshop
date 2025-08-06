package com.sysc.workshop.product.repository;

import com.sysc.workshop.product.model.Product;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory_Name(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategory_NameAndBrand(String category, String brand);
    // findByCategoryNameAndBrand; this method name assume "categoryName" is variable of product
    // the code above is the correct way of going to the object category and find the "name" variable

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    long countByBrandAndName(String brand, String name);
    boolean existsByNameAndBrand(String name, String brand);



    @Query("SELECT p FROM Product p WHERE " +
            "(:name IS NULL OR p.name LIKE %:name%) AND " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:category IS NULL OR p.category = :category)")
    Page<Product> searchProducts(
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("category") String category,
            Pageable pageable
    );
}
