package com.sysc.workshop.product.service.category;

import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.dto.category.SearchCategoryRequestDTO;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.model.Category;
import java.util.List;
import java.util.UUID;

public interface ICategoryService {
    CategoryDto getCategoryById(UUID id);
    Category getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    CategoryDto addCategory(String category);
    CategoryDto updateCategory(String category, UUID id);
    void deleteCategoryById(UUID id);
    PagedResponseDTO<CategoryDto> searchCategories(
        SearchCategoryRequestDTO requestDTO
    );
}
