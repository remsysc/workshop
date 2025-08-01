package com.sysc.workshop.product.service.category;

import com.sysc.workshop.product.model.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryService {

    Category getCategoryById(UUID id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, UUID id);
    void deleteCategoryById(UUID id);

}
