package com.sysc.workshop.product.repository;

import com.sysc.workshop.product.model.Category;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);

    boolean existsByName(String name);

    Page<Category> findByNameContainingIgnoreCase(
        String category,
        PageRequest pageable
    );
}
